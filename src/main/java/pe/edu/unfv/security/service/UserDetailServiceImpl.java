package pe.edu.unfv.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.controller.dto.AuthCreateUserRequest;
import pe.edu.unfv.controller.dto.AuthLoginRequest;
import pe.edu.unfv.controller.dto.AuthResponse;
import pe.edu.unfv.persistence.entity.RoleEntity;
import pe.edu.unfv.persistence.entity.UserEntity;
import pe.edu.unfv.persistence.repository.RoleRepository;
import pe.edu.unfv.persistence.repository.UserRepository;
import pe.edu.unfv.util.JwtUtils;

@Service
@Primary
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

	private UserRepository userRepository;
	private JwtUtils jwtUtils;
	private PasswordEncoder passwordEncoder;
	private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		UserEntity userEntity = userRepository.findUserEntityByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));
		
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		userEntity.getRoles()
			.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
		
		userEntity.getRoles()
			.stream()
			.flatMap(role -> role.getPermissionList().stream())
			.forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
		
		return new User(
				userEntity.getUsername(),
				userEntity.getPassword(),
				userEntity.isEnabled(),
				userEntity.isAccountNoExpired(),
				userEntity.isCredentialNoExpired(),
				userEntity.isAccountNoLocked(),
				authorityList
				);
	}
	
	public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
		
		String username = authLoginRequest.username();
		String password = authLoginRequest.password();
		
		Authentication authentication = this.authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String accessToken = jwtUtils.createToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(username, "User loged successfuly", accessToken, true);
		
		return authResponse;
	}	
	
	public Authentication authenticate(String username, String password) {
		
		UserDetails userDetails = this.loadUserByUsername(username);
		
		if(userDetails == null) {
			throw new BadCredentialsException("Invalid email or password.");
		}
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Invalid  password.");
		}
		
		return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
	}

	public AuthResponse createUser(AuthCreateUserRequest authCreateUserRequest) {
		
		String username = authCreateUserRequest.username();
		String password = authCreateUserRequest.password();
		String email = authCreateUserRequest.email();
		List<String> roleRequest = authCreateUserRequest.roleRequest().roleListName();
		
		Optional<UserEntity> usernameExist = userRepository.findUserEntityByUsername(username);
		
		Optional<UserEntity> emailExist = userRepository.findUserEntityByEmail(email);
		
		Set<RoleEntity> roleEntitySet = roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest).stream().collect(Collectors.toSet());
		
		if (usernameExist.isPresent()) {
			
			AuthResponse authResponseUser = new AuthResponse(username, "The user exist in our data base.", "Not generated.", false);
			return authResponseUser;
		}
		
		if (emailExist.isPresent()) {
			
			AuthResponse authResponseEmail = new AuthResponse(username, "The email exist in our data base.", "Not generated.", false);
			return authResponseEmail;
		}
		
		if (roleEntitySet.isEmpty()) {			
			
			AuthResponse authResponse = new AuthResponse(username, "The specified roles do not exist or none were entered.", "Not generated.", false);
			return authResponse;
		}		
		
		UserEntity userEntity = UserEntity.builder()
				.username(username)
				.password(passwordEncoder.encode(password))	
				.email(email)
				.isEnabled(true)
				.accountNoLocked(true)
				.accountNoExpired(true)
				.credentialNoExpired(true)
				.roles(roleEntitySet)
				.build();
				
		UserEntity userCreated = userRepository.save(userEntity);
		
		ArrayList<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		userCreated.getRoles().forEach(role ->
			authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name())))
				);
		
		userCreated.getRoles()
			.stream()
			.flatMap(role -> role.getPermissionList().stream())
			.forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));
				
		Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername(), userCreated.getPassword(), authorityList);
		
		String accesToken = this.jwtUtils.createToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(userCreated.getUsername(), "User created successfully", accesToken, true);
		
		return authResponse;		
	}
}
