package pe.edu.unfv.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import pe.edu.unfv.jwt.JwtTokenFilter;
import pe.edu.unfv.jwt.JwtUtils;
import pe.edu.unfv.security.filter.JwtTokenValidator;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class Seguridad {

	private JwtTokenFilter jwtTokenFilter;
	
	private JwtUtils jwtUtils;
	
	@Bean
	AuthenticationManager authenticationManage(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	/*
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity
                .csrf(csrf -> csrf.disable())                
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))                		        
				.build();
	}
	*/
	
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity
                .csrf(csrf -> csrf.disable())                
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, ex) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        }))
                //.addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationToken.class)         
                .authorizeHttpRequests(authorizeHttpRequests -> {                	
                	
                	//Configurar los endpoints publicos                	
                	authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/api/v1/login").permitAll();
                	//authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/api/v1/categorias").permitAll();
                	authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                	
                	//Configurar los endpoints privados
                	//authorizeHttpRequests.requestMatchers(HttpMethod.GET, "/api/v1/categorias");     	
                	
                	//Configurar el resto de endpoints - NO ESPECIFICADOS
                	authorizeHttpRequests.anyRequest().denyAll();
                })
		
                .addFilterBefore(new JwtTokenValidator(this.jwtUtils), BasicAuthenticationFilter.class)
				.build();
	}
	
	
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
