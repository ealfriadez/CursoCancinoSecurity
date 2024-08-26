package pe.edu.unfv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
public class NewAuthenticationController {

	private UserDetailServiceImpl userDetailServiceImpl;
	
	@PostMapping("/sign-up-new")
	public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest){
		
		return new ResponseEntity<>(this.userDetailServiceImpl.createUser(authCreateUserRequest), HttpStatus.CREATED);
	}
	
	@PostMapping("/log-in-new")
	public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest userRequest, BindingResult bindingResult){
		
		if (!this.userDetailServiceImpl.loginUser(userRequest).equals(null)) {
			return new ResponseEntity<AuthResponse>(this.userDetailServiceImpl.loginUser(userRequest), HttpStatus.OK);
		} 
		
		try {			
			           
        } catch (Exception e) {
            return new ResponseEntity<>("Error registering category: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
		
		return new ResponseEntity<>(this.userDetailServiceImpl.loginUser(userRequest), HttpStatus.OK);
	}
}
