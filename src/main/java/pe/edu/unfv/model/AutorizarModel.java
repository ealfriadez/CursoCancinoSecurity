package pe.edu.unfv.model;

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
@Table(name = "autorizar")
public class AutorizarModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	
	@OneToOne
	@JoinColumn(name = "usuarios_id")
	private UsuariosModel usuariosId;

	public AutorizarModel(String nombre, UsuariosModel usuariosId) {
		super();
		this.nombre = nombre;
		this.usuariosId = usuariosId;
	}	
}
