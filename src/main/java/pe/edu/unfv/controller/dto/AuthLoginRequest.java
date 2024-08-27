package pe.edu.unfv.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginRequest(
		
		@NotBlank(message = "The user name is required.")
		//@Email(message = "User name must be a correctly formatted email address.")
		@Size(min = 4, message = "The user name must be at least 4 characters.")
		String username,
		
		@NotBlank(message = "The password is required.")
		@Size(min = 4, message = "The password must be at least 4 characters.")
		String password
		) {
}
