package pe.edu.unfv.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.persistence.entity.UserEntity;
import pe.edu.unfv.persistence.repository.UserRepository;

@Service
@Primary
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService{

	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		
		UserEntity userEntity = userRepository.findByCorreo(correo)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + correo + " no existe"));
		
		List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
		
		userEntity.getRoles()
			.forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));
		
		userEntity.getRoles().stream()
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
}
