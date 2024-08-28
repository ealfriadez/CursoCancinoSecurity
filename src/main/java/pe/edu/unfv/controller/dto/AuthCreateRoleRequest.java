package pe.edu.unfv.controller.dto;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Size;

@Validated
public record AuthCreateRoleRequest(
				
		//maximo de roles de un usuario		
		@Size(max = 3, message = "The user cannot have more than 3 roles") 
		List<String> roleListName
		
		) {
}
