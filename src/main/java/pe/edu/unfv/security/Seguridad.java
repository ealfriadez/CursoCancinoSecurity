package pe.edu.unfv.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import pe.edu.unfv.jwt.JwtTokenFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class Seguridad {

	private JwtTokenFilter jwtTokenFilter;
	
	@Bean
	AuthenticationManager authenticationManage(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {

		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}

	@SuppressWarnings("unchecked")
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf((csrf) ->
                        csrf.disable())
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(handling -> handling
                        .authenticationEntryPoint((request, response, ex) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                        }))
                .addFilterBefore(this.jwtTokenFilter, UsernamePasswordAuthenticationToken.class)         
                .authorizeHttpRequests((authorizeHttpRequests) ->
                	authorizeHttpRequests.requestMatchers("/api/v1/login").permitAll().anyRequest().authenticated());                
		
		return httpSecurity			        
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
