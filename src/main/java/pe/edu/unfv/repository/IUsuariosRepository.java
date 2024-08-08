package pe.edu.unfv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.unfv.model.UsuariosModel;

@Repository
public interface IUsuariosRepository extends JpaRepository<UsuariosModel, Integer>{

	UsuariosModel findByCorreoAndEstado(String correo, Integer estado);
}
