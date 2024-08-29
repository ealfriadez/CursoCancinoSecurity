package pe.edu.unfv.service;

import java.util.List;

import pe.edu.unfv.persistence.dto.CategoryDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;

public interface ICategoriasService {

	List<CategoriasModel> getAllCategorys();
	boolean existsCategoryById(Integer id);
	boolean existsCategoryByName(String name);
	boolean existsCategoryByNameExcludingId(String name, int id);
	CategoryDTO saveCategory(CategoryDTO categoryDTO);
	CategoriasModel getCategoryModelById(int id);
	CategoryDTO getCategoryById(Integer id);
	void saveCategoryModel(CategoriasModel categoriasModel);
	void deleteCategory(int id);
}
