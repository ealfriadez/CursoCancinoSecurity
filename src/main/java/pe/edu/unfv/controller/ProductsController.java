package pe.edu.unfv.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;
import pe.edu.unfv.persistence.entity.model.ProductosModel;
import pe.edu.unfv.service.implement.CategoriasServiceImpl;
import pe.edu.unfv.service.implement.ProductosServiceImpl;
import pe.edu.unfv.util.ImageUtils;
import pe.edu.unfv.util.Utilidades;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ProductsController {

	private ProductosServiceImpl productosServiceImpl;
	private CategoriasServiceImpl categoriasServiceImpl;
	
	@GetMapping("/products")
	public List<ProductosModel> prudcts() {

		return this.productosServiceImpl.getAllProducts();
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<?> productsById(@PathVariable("id") int id) {

		if (!this.productosServiceImpl.existsProductById(id)) {
			return Utilidades.generateResponse(HttpStatus.NOT_FOUND,
					"Product with id: ".concat(id + " does not exist"));
		}

		try {
			ProductosModel productosModel = this.productosServiceImpl.getProductModelById(id);
			return ResponseEntity.ok(productosModel);
		} catch (Exception e) {
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error retrieving category: " + e.getMessage());
		}
	}
	
	@SuppressWarnings("static-access")
	@PostMapping("/products")
	public ResponseEntity<?> saveProductUploadImage(
			@RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("precio") int precio,
			@RequestParam("categoria") int categoria,
			@RequestParam("image") MultipartFile file) 
			throws IOException {			
		
		if (this.productosServiceImpl.existsProductByName(nombre)) {            
            return Utilidades.generateResponse(HttpStatus.CONFLICT, "Product name already exists. Please choose another name.");
        }
		
		if (nombre.isEmpty() || descripcion.isEmpty() || precio <= 0 || categoria <= 0) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "One or more fields are required to register the product, please review it.");
		}
		
		if (!this.categoriasServiceImpl.existsCategoryById(categoria)) {			
			return Utilidades.generateResponse(HttpStatus.NOT_FOUND, "Category with id: ".concat(categoria +" does not exist"));
		}
		
		if (file.isEmpty()) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "The photo submitted is not valid, it must be JPG|PNG.");
		}
		
		String nombreImagen = Utilidades.guardarArchivo(file, null);
		if (nombreImagen == "no" || nombreImagen == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "The photo submitted is not valid, it must be JPG|PNG.");
		}		

		try {			
			this.productosServiceImpl.saveProductImage(nombre, descripcion, precio, categoria, file, nombreImagen).builder().build();
			return Utilidades.generateResponse(HttpStatus.CREATED, "Product created successfully.");			
		} catch (Exception e) {			
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error in the image upload: " + e.getMessage());
		}		
	}
	
	@SuppressWarnings("static-access")
	@PutMapping("/products/{id}")
	public ResponseEntity<?> updateProductUploadImage(
			@PathVariable int id,
			@RequestParam("nombre") String nombre,
			@RequestParam("descripcion") String descripcion,
			@RequestParam("precio") int precio,
			@RequestParam("categoria") int categoria,
			@RequestParam("image") MultipartFile file) 
			throws IOException {			
		
		if (this.productosServiceImpl.existsProductByName(nombre)) {            
            return Utilidades.generateResponse(HttpStatus.CONFLICT, "Product name already exists. Please choose another name.");
        }
		
		if (nombre.isEmpty() || descripcion.isEmpty() || precio <= 0 || categoria <= 0) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "One or more fields are required to register the product, please review it.");
		}
		
		if (!this.categoriasServiceImpl.existsCategoryById(categoria)) {			
			return Utilidades.generateResponse(HttpStatus.NOT_FOUND, "Category with id: ".concat(categoria +" does not exist"));
		}
		
		if (file.isEmpty()) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "The photo submitted is not valid, it must be JPG|PNG.");
		}
		
		String nombreImagen = Utilidades.guardarArchivo(file, null);
		if (nombreImagen == "no" || nombreImagen == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "The photo submitted is not valid, it must be JPG|PNG.");
		}	
		
		 if (!productosServiceImpl.existsProductById(id)) {			 
			 return Utilidades.generateResponse(HttpStatus.NOT_FOUND, "The specified product does not exist. Please verify the entered data.");
	     }
	            
	     if (!categoriasServiceImpl.existsCategoryById(categoria)) {	    	 
	    	 return Utilidades.generateResponse(HttpStatus.NOT_FOUND, "The specified category does not exist. Please verify the entered data.");
	     }
		
		try {
			
			CategoriasModel categoriasModel = new CategoriasModel();
			categoriasModel.setId(categoria);
			
			ProductosModel productosModel = this.productosServiceImpl.getProductModelById(id);
			productosModel.setCategoriaId(categoriasModel);
			productosModel.setNombre(nombre);
			productosModel.setSlug(Utilidades.getSlug(nombre));
			productosModel.setDescripcion(descripcion);
			productosModel.setPrecio(precio);
			productosModel.setNombreFoto(nombreImagen);
			productosModel.setTipoFoto(file.getContentType());
			productosModel.setImageData(ImageUtils.compressImage(file.getBytes()));			
			
			this.productosServiceImpl.saveProductoModel(productosModel);
			return Utilidades.generateResponse(HttpStatus.CREATED, "Product update successfully.");			
		} catch (Exception e) {			
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error in the image upload: " + e.getMessage());
		}		
	}	
	
	@DeleteMapping("/products/{id}")
	public ResponseEntity<?> deleteProductById(@PathVariable("id") int id) {
		
		if (!this.productosServiceImpl.existsProductById(id)) {			
			return Utilidades.generateResponse(HttpStatus.NOT_FOUND, "Product with id: ".concat(id +" does not exist"));
		}
		
		try {
			this.productosServiceImpl.deleteProduct(id);
			return Utilidades.generateResponse(HttpStatus.OK, "Product deleted successfully.");
		} catch (Exception e) {
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving category: " + e.getMessage());			
		}		
	}
	
	@GetMapping("/products/image/{nombreImagen}")
    public ResponseEntity<?> downloadImage(@PathVariable String nombreImagen){
        
		byte[] imageInbytes =  this.productosServiceImpl.downloadImage(nombreImagen);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png")).body(imageInbytes);

    }
}
