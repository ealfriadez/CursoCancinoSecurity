package pe.edu.unfv.persistence.entity.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
@Builder
@JsonPropertyOrder({"id", "nombre", "descripcion", "slug", "precio", "tipoFoto", "nombreFoto"})
public class ProductosModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;	
	
	private String nombre;
	
	private String slug;
	
	private String descripcion;
	
	private int precio;
	
	private String nombreFoto;

	private String tipoFoto;

	@JsonIgnore
	@Lob
	@Column(name="imageData")
	private byte[] imageData;
	
	@OneToOne
	@JoinColumn(name = "categoria_id")
	private CategoriasModel categoriaId;

	public ProductosModel(int id, String nombre, String slug, String descripcion, int precio, String nombreFoto,
			String tipoFoto) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.slug = slug;
		this.descripcion = descripcion;
		this.precio = precio;
		this.nombreFoto = nombreFoto;
		this.tipoFoto = tipoFoto;
	}

	public ProductosModel(int id, String nombre, String slug, String descripcion, int precio, String nombreFoto,
			String tipoFoto, CategoriasModel categoriaId) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.slug = slug;
		this.descripcion = descripcion;
		this.precio = precio;
		this.nombreFoto = nombreFoto;
		this.tipoFoto = tipoFoto;
		this.categoriaId = categoriaId;
	}	
}
