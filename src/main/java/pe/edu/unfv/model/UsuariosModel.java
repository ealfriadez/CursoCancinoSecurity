package pe.edu.unfv.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class UsuariosModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	
	@Column(unique = true)
	private String correo;
	
	private String telefono;
	
	private String password;
	
	private int estado;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "usuarios_id")
	private List<AutorizarModel> autorizar;

	public UsuariosModel(String nombre, String correo, String telefono, String password, int estado,
			List<AutorizarModel> autorizar) {
		super();
		this.nombre = nombre;
		this.correo = correo;
		this.telefono = telefono;
		this.password = password;
		this.estado = estado;
		this.autorizar = autorizar;
	}
}
