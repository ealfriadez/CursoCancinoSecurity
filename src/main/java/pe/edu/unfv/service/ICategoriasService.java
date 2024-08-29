package pe.edu.unfv.service;

import java.util.List;

import pe.edu.unfv.persistence.dto.CategoryDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;

public interface ICategoriasService {

	List<CategoriasModel> getAllCategorys();
	//CategoriasModel getCategoryById(Integer id);	
	//void saveCategory(CategoriasModel category);
	void deleteCategory(Integer id);	
	
	boolean existsCategoryById(Integer id);
	boolean existsCategoryByName(String name);
	boolean existsCategoryByNameExcludingId(String name, int id);
	CategoryDTO saveCategory(CategoryDTO categoryDTO);
	CategoryDTO getCategoryById(Integer id);
}
