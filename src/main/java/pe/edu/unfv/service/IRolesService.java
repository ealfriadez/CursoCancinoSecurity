package pe.edu.unfv.service;

import java.util.List;
import java.util.Optional;

import pe.edu.unfv.model.RolesModel;

public interface IRolesService {

	List<RolesModel> getAllRol();
	Optional<RolesModel> getRolById(Integer id);	
	void saveAutorizar(RolesModel rol);
	void deleteAutorization(Integer id);
}
