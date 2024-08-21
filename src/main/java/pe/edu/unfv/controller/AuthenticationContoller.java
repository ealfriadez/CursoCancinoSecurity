package pe.edu.unfv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.edu.unfv.dto.AuthLoginRequest;
import pe.edu.unfv.dto.AuthResponse;
import pe.edu.unfv.security.UsuarioLogin;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationContoller {

	private UsuarioLogin usuarioLogin;
	
	@PostMapping("/log-in")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
		
		return new ResponseEntity<>(this.usuarioLogin.loginUser(userRequest), HttpStatus.OK);
	}
}
