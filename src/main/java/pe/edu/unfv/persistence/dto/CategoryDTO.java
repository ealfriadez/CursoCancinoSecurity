package pe.edu.unfv.persistence.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

	@NotBlank(message = "The category name is required")
	@Size(min = 4, max = 50, message = "The category name must be between 4 and 50 characters")
    private String nombre;
	
	private String slug;
}
