package pe.edu.unfv.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginPersonalizado extends SimpleUrlAuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain chain,
			Authentication authentication) throws IOException, ServletException {
		
		System.out.println("Demoooooooooooooooooooo");
		
		response.sendRedirect("/");		
		
		super.onAuthenticationSuccess(request, response, chain, authentication);
	}
}
