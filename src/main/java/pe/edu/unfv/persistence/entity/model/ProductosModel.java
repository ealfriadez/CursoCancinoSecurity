package pe.edu.unfv.persistence.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
	
	private String nombre;
	
	private String slug;
	
	private String descripcion;
	
	private int precio;
	
	private String foto;	
	
	@OneToOne
	@JoinColumn(name = "categoria_id")
	private CategoriasModel categoriaId;	
}
