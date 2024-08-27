package pe.edu.unfv.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.edu.unfv.controller.dto.AuthCreateUserRequest;
import pe.edu.unfv.controller.dto.AuthLoginRequest;
import pe.edu.unfv.controller.dto.ResponseNew;
import pe.edu.unfv.security.service.UserDetailServiceImpl;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {

	private UserDetailServiceImpl userDetailServiceImpl;
	
	@PostMapping("/sign-up-new")
	public ResponseEntity<?> register(@RequestBody @Valid AuthCreateUserRequest authCreateUserRequest, BindingResult result){
		
		if (result.hasErrors()) {
            String firstErrorMessage = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown validation error");
            return new ResponseEntity<>(new ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST);
        }	
		
		return new ResponseEntity<>(this.userDetailServiceImpl.createUser(authCreateUserRequest), HttpStatus.CREATED);
	}
	
	@PostMapping("/log-in-new")
	public ResponseEntity<?> login(@RequestBody @Valid AuthLoginRequest userRequest, BindingResult result){
		
		if (result.hasErrors()) {
            String firstErrorMessage = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown validation error");
            return new ResponseEntity<>(new ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST);
        }
		
		if (!this.userDetailServiceImpl.loginUser(userRequest).status()) {
			
			return new ResponseEntity<>(this.userDetailServiceImpl.loginUser(userRequest), HttpStatus.UNAUTHORIZED);
		}
		
		return new ResponseEntity<>(this.userDetailServiceImpl.loginUser(userRequest), HttpStatus.OK);
	}
}
