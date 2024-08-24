package pe.edu.unfv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.edu.unfv.controller.dto.AuthCreateUserRequest;
import pe.edu.unfv.controller.dto.AuthLoginRequest;
import pe.edu.unfv.controller.dto.AuthResponse;
import pe.edu.unfv.security.service.UserDetailServiceImpl;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

	private UserDetailServiceImpl userDetailServiceImpl;
	
	@PostMapping("/sign-up")
	public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest){
		
		return new ResponseEntity<>(this.userDetailServiceImpl.createUser(authCreateUserRequest), HttpStatus.CREATED);
	}
	
	@PostMapping("/log-in")
	public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
		
		return new ResponseEntity<>(this.userDetailServiceImpl.loginUser(userRequest), HttpStatus.OK);
	}
}
