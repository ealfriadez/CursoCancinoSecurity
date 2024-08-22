package pe.edu.unfv.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;
import pe.edu.unfv.jwt.JwtUtils;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class Seguridad {	
	
	private JwtUtils jwtUtils;
	
	@Bean
	AuthenticationManager authenticationManage(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}
	
	/*
	 * @Bean BCryptPasswordEncoder passwordEncoder() {
	 * 
	 * return new BCryptPasswordEncoder(); }
	 */
	
	@Bean
	UserDetailsService userDetailsService() {
	
		List<UserDetails> userDetails = new ArrayList<>();
		
		userDetails.add(User.withUsername("santiago")
				.password("1234")
				.roles("ADMIN")
				.authorities("READ","CREATE")
				.build());
		
		userDetails.add(User.withUsername("sebatian")
				.password("1234")
				.roles("USER")
				.authorities("READ")
				.build());
		
		return new InMemoryUserDetailsManager(userDetails);
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	AuthenticationProvider authenticationProvider() {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(this.passwordEncoder());
		provider.setUserDetailsService(this.userDetailsService());
		
		return provider;
	}	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity                             		        
				.build();
	}
	
	
	/*
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity
                .csrf(csrf -> csrf.disable())                
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, ex) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        }))                        
                .authorizeHttpRequests(authorizeHttpRequests -> {                	
                	
                	//Configurar los endpoints publicos                	
                	authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll();
                	authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/api/v1/categorias").permitAll();
                	authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                	
                	//Configurar los endpoints privados
                	//authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/api/v1/categorias");     	
                	
                	//Configurar el resto de endpoints - NO ESPECIFICADOS
                	authorizeHttpRequests.anyRequest().denyAll();
                })
		
                .addFilterBefore(new JwtTokenValidator(this.jwtUtils), BasicAuthenticationFilter.class)
				.build();
	}
	*/
	
	/*
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity
			.csrf((csrf) -> 
				csrf.disable())	
			.authorizeHttpRequests((authorizeHttpRequests) ->
				authorizeHttpRequests.anyRequest().permitAll())
			.sessionManagement((session) -> 
				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		return httpSecurity			        
				.build();
	}
	*/
}
