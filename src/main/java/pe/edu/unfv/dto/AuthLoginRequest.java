package pe.edu.unfv.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
		@NotBlank String correo,
		@NotBlank String password) {

}
