package pe.edu.unfv.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import pe.edu.unfv.dto.AuthLoginRequest;
import pe.edu.unfv.dto.AuthResponse;
import pe.edu.unfv.jwt.JwtUtils;
import pe.edu.unfv.model.UsuariosModel;
import pe.edu.unfv.service.implement.UsuariosServiceImpl;
import pe.edu.unfv.util.Constantes;

@Component
@AllArgsConstructor
public class UsuarioLogin implements UserDetailsService {

	
	private UsuariosServiceImpl usuariosServiceImpl;
	
	private JwtUtils jwtUtils;
	
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UsuariosModel usuariosModel = this.usuariosServiceImpl.getUserByEmailAndEstate(username, Constantes.UNO);

		if (usuariosModel == null) {
			throw new UsernameNotFoundException(Constantes.EL_EMAIL.concat(username).concat(Constantes.NO_EXISTE));
		}

		// configuramos los autorities
		List<GrantedAuthority> authorities = new ArrayList<>();		

		// retornamos el userDetail con los datos del usuario logueado
		return new User(usuariosModel.getNombre(), usuariosModel.getPassword(), true, true, true, true, authorities);
	}
	
	public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {
	
		String username = authLoginRequest.correo();
		String password = authLoginRequest.password();
		
		Authentication authentication = this.authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String accessToken = jwtUtils.createToken(authentication);
		
		AuthResponse authResponse = new AuthResponse(username, "User loged successfuly", accessToken, true);
		
		return authResponse;
	}
	
	public Authentication authenticate(String username, String password) {
		
		UsuariosModel usuariosModel = this.usuariosServiceImpl.getUserByEmailAndEstate(username, Constantes.UNO);
			
		if (usuariosModel == null) {
			throw new BadCredentialsException("Invalid username or password");
		}
		
		if (!passwordEncoder.matches(password, usuariosModel.getPassword())) {
			throw new BadCredentialsException("Invalid password");
		}
		
		return new UsernamePasswordAuthenticationToken(username, usuariosModel.getPassword(), null);
	}

}
