package pe.edu.unfv.service;

import java.util.List;

import pe.edu.unfv.persistence.entity.model.ProductosModel;

public interface IProductosService {

	List<ProductosModel> getAllProducts();
	ProductosModel getProductById(Integer id);	
	void saveProduct(ProductosModel product);
	void deleteProduct(Integer id);
}
