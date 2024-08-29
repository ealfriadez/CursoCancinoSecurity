package pe.edu.unfv.persistence.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class ProductosModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	@NotEmpty(message = " esta vacio")
	private String nombre;
	
	private String slug;
	@NotEmpty(message = " esta vacio") 
	private String descripcion;
	@NotNull(message = " no puede ser nulo") 
	@Min(5)
	//@Max(5000)
	private int precio;
	
	private String foto;	
	
	@OneToOne
	@JoinColumn(name = "categoria_id")
	private CategoriasModel categoriaId;	
}
