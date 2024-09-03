package pe.edu.unfv.persistence.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {

	@NotBlank(message = "Product name is required")
    @Size(min = 4, max = 50, message = "Product name must be between 4 and 50 characters")
	private String nombre;	
	
	@NotBlank(message = "Product description is required")
	@Size(min =10, message = "Product description must be at least 10 characters")
    private String descripcion;	
	
	@NotNull(message = "Price cannot be null")
    @Min(value = 1, message = "Price must be a positive value")
	private int precio;	
    
	@NotNull(message = "Category cannot be null")
    private int categoria;
}
