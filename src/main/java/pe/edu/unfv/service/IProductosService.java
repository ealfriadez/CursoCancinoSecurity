package pe.edu.unfv.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
	void saveProductoModel(ProductosModel productosModel);
	
	ProductosModel saveProductImage(String nombre, String descripcion, int precio, int categoria, MultipartFile file, String nombreImagen) throws IOException;
	
	byte[] downloadImage(String nombreImagen);
}
