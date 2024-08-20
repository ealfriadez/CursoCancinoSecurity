package pe.edu.unfv.service;

import java.util.List;

import pe.edu.unfv.model.CategoriasModel;

public interface ICategoriasService {

	List<CategoriasModel> getAllCategorys();
	CategoriasModel getCategoryById(Integer id);	
	void saveCategory(CategoriasModel category);
	void deleteCategory(Integer id);
}
