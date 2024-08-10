package pe.edu.unfv.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class Seguridad {
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
       
		httpSecurity.authorizeHttpRequests(
				
				requests -> requests

                //las vistas que seran publicas o no requieran autenticacion
                .requestMatchers(
                        "/",
                        "/liberada/**",
                        "/acceso/registro"
                )
                .permitAll()

                //asignar permisos a los recursos estaticos
                .requestMatchers(
                        "/images/**",
                        "/js/**",
                        "/css/**"
                ).permitAll()

                //asignar permisos a las URLs por Roles
                .requestMatchers(
                        "/protegido/**"
                ).hasAnyAuthority("ROLE_ADMIN")
				//).hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")

                //se hacen las configuraciones generales
                .anyRequest().authenticated())
						//.formLogin(login -> login.permitAll())
						.formLogin(login -> login.permitAll().loginPage("/acceso/login"))
						.logout(logout -> logout.permitAll());
		
		return httpSecurity.build();
	}
	
	/*
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		
		return (web) -> web.ignoring().requestMatchers(
				"/images/**",
				"/js/**",
				"/css/**"
				);				
	}
	*/
}











