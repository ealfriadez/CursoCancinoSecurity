package pe.edu.unfv.service;

import java.util.List;
import java.util.Optional;

import pe.edu.unfv.model.AutorizarModel;

public interface IAutorizarService {

	List<AutorizarModel> getAllNameRol();
	Optional<AutorizarModel> getAutorizationByNameRol(Integer id);	
	AutorizarModel saveCity(AutorizarModel city);
	void deleteAutorization(Integer id);
}
