package pe.edu.unfv.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class Seguridad {		
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity 
				.csrf(csrf -> csrf.disable()) //se desabilita porque no se necesita esto es solo para WEB				
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(http -> {
						
						//configurar los endpoints publicos
						http.requestMatchers(HttpMethod.GET, "/api/v1/demo").permitAll();
						
						//configurar los endpoint privados
						http.requestMatchers(HttpMethod.GET, "/api/v1/demo-security").hasAuthority("CREATE");
					
						//configurar el resto de enpoins - NO ESPECIFICADOS
						http.anyRequest().denyAll();
					})
				.build();
	}
	
	@Bean
	AuthenticationManager authenticationManage(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(this.passwordEncoder());
		provider.setUserDetailsService(userDetailsService);
		
		return provider;
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return NoOpPasswordEncoder.getInstance();
	}	
}
