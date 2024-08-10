package pe.edu.unfv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.unfv.model.UsuariosModel;

public interface IUsuariosRepository extends JpaRepository<UsuariosModel, Integer>{

	UsuariosModel findByCorreoAndEstado(String correo, Integer estado);
}
