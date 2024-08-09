package pe.edu.unfv.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import pe.edu.unfv.model.AutorizarModel;
import pe.edu.unfv.model.UsuariosModel;
import pe.edu.unfv.service.implement.UsuariosServiceImpl;
import pe.edu.unfv.util.Constantes;

@Component
@AllArgsConstructor
public class UsuarioLogin implements UserDetailsService{

	private UsuariosServiceImpl usuariosServiceImpl;
	
	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UsuariosModel usuario = this.usuariosServiceImpl.getUserByEmailAndEstate(username, Constantes.UNO);
		
		if (usuario==null) {
			throw new UsernameNotFoundException(Constantes.EL_EMAIL.concat(username).concat(Constantes.NO_EXISTE));
		} 
		
		//configuramos los autorities
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		for (AutorizarModel role : usuario.getAutorizar()) {
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		}
		System.out.println(authorities);
		
		if (authorities.isEmpty()) {
			throw new UsernameNotFoundException(Constantes.EL_EMAIL.concat(username).concat(Constantes.NO_ROL));
		}
		
		//retornamos el userDetail con los datos del usuario logueado
		return new User(usuario.getNombre(), usuario.getPassword(), true, true, true, true, authorities);	}

}
