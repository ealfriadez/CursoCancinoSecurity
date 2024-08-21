package pe.edu.unfv.security.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import pe.edu.unfv.jwt.JwtUtils;

@AllArgsConstructor
public class JwtTokenValidator extends OncePerRequestFilter{

	private JwtUtils jwtUtils;
	
	@Override
	protected void doFilterInternal(
			@NonNull HttpServletRequest request, 
			@NonNull HttpServletResponse response, 
			@NonNull FilterChain filterChain) throws ServletException, IOException {
		
		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if (jwtToken != null) { //Bearer asdasd32a1d2as1d1asd1asd132a1sd
			jwtToken = jwtToken.substring(7);
			
			DecodedJWT decodedJWT = jwtUtils.validateToken(jwtToken);
			
			String username = jwtUtils.extractUsername(decodedJWT);
			
			//falta añadir la parte para las autorizaciones
			String stringAuthorities = jwtUtils.getSpecificClaim(decodedJWT, "authorities").asString();			
			Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
		
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
			context.setAuthentication(authentication);
			SecurityContextHolder.setContext(context);
		}
		
		filterChain.doFilter(request, response);
	}

}
