package pe.edu.unfv.service;

import java.util.List;

import pe.edu.unfv.persistence.dto.ProductDTO;
import pe.edu.unfv.persistence.entity.model.ProductosModel;

public interface IProductosService {
		
	void deleteProduct(Integer id);
	
	List<ProductosModel> getAllProducts();
	boolean existsProductById(int id);
	boolean existsProductByName(String name);
	ProductDTO getProductDTOById(int id);
	ProductosModel getProductModelById(int id);
	ProductDTO saveProduct(ProductDTO productDTO);
	
	/*
	
	boolean existsCategoryByNameExcludingId(String name, int id);	
	CategoriasModel getCategoryModelById(int id);
	CategoryDTO getCategoryById(Integer id);
	void saveCategoryModel(CategoriasModel categoriasModel);
	void deleteCategory(int id);
	*/
}
