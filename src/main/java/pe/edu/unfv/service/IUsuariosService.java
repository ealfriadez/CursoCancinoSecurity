package pe.edu.unfv.service;

import java.util.List;
import java.util.Optional;

import pe.edu.unfv.model.UsuariosModel;

public interface IUsuariosService {

	List<UsuariosModel> getAllNameUser();
	Optional<UsuariosModel> getUserById(Integer id);	
	UsuariosModel saveCity(UsuariosModel usuario);
	void deleteUser(Integer id);
}
