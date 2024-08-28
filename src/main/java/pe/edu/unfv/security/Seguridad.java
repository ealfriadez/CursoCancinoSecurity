package pe.edu.unfv.security;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import lombok.AllArgsConstructor;
import pe.edu.unfv.security.filter.JwtTokenValidator;
import pe.edu.unfv.util.security.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class Seguridad {		
	
	private JwtUtils jwtUtils;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity,  AuthenticationProvider authenticationProvide) throws Exception {

		return httpSecurity 
				.csrf(csrf -> csrf.disable()) //se desabilita porque no se necesita esto es solo para WEB				
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				
				//Configurar los endpoints publicos
				.authorizeHttpRequests(http -> {
						
					//Configurar los endpoints publicos
					//http.requestMatchers(HttpMethod.GET, "/method/get").permitAll();
					http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
					http.requestMatchers(HttpMethod.GET, "/v1/categorys/**").permitAll();					
					
					//Configurar los endpoints privados
					//http.requestMatchers(HttpMethod.POST, "/api/v1/post").hasAnyAuthority("CREATE", "READ");
					http.requestMatchers(HttpMethod.POST, "/method/post").hasAnyRole("ADMIN", "DEVELOPER");
					http.requestMatchers(HttpMethod.PATCH, "/method/patch").hasAnyAuthority("REFACTOR");
					http.requestMatchers(HttpMethod.GET, "/method/get").hasAnyRole("INVITED");
					
					http.requestMatchers(HttpMethod.POST, "/v1/").hasAnyRole("ADMIN", "DEVELOPER");
					http.requestMatchers(HttpMethod.PUT, "/v1/{id}").hasAnyRole("ADMIN", "DEVELOPER");
				
					//Confifurar el resto de endpoints - NO ESPECIFICADOS
					http.anyRequest().denyAll();
				})
				
				.addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
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
		
		return new BCryptPasswordEncoder();
	}		
}
