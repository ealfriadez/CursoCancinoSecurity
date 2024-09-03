package pe.edu.unfv.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import pe.edu.unfv.persistence.entity.model.ProductosModel;
import pe.edu.unfv.service.implement.CategoriasServiceImpl;
import pe.edu.unfv.service.implement.ImageDataServiceImpl;
import pe.edu.unfv.service.implement.ProductosServiceImpl;
import pe.edu.unfv.util.Utilidades;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ProductsController {

	private ProductosServiceImpl productosServiceImpl;
	private CategoriasServiceImpl categoriasServiceImpl;
	private ImageDataServiceImpl imageDataServiceImpl;

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

	@PostMapping("/products")
	public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

		if (file.isEmpty()) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
					"The photo submitted is not valid, it must be JPG|PNG.");
		}

		String nombreImagen = Utilidades.guardarArchivo(file, null);
		if (nombreImagen == "no" || nombreImagen == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
					"The photo submitted is not valid, it must be JPG|PNG.");
		}

		try {
			this.imageDataServiceImpl.uploadImage(file, nombreImagen);
			return Utilidades.generateResponse(HttpStatus.CREATED, "Image upload successfully.");
		} catch (Exception e) {
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error in the image upload: " + e.getMessage());
		}
	}
	
	@PostMapping("/products/A")
	public ResponseEntity<?> uploadImageX(@RequestParam("image") MultipartFile file) throws IOException {
		
		if (file.isEmpty()) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
					"The photo submitted is not valid, it must be JPG|PNG.");
		}

		String nombreImagen = Utilidades.guardarArchivo(file, null);
		if (nombreImagen == "no" || nombreImagen == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
					"The photo submitted is not valid, it must be JPG|PNG.");
		}

		try {
			this.imageDataServiceImpl.uploadImage(file, nombreImagen);
			return Utilidades.generateResponse(HttpStatus.CREATED, "Image upload successfully.");
		} catch (Exception e) {
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error in the image upload: " + e.getMessage());
		}
	}
	
	@SuppressWarnings("static-access")
	@PostMapping("/products/B")
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
			return Utilidades.generateResponse(HttpStatus.CREATED, "Image upload successfully.");			
		} catch (Exception e) {			
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error in the image upload: " + e.getMessage());
		}		
	}	

	/*
	 * @PutMapping("/{id}") public ResponseEntity<?>
	 * updateCategory(@PathVariable("id") int id, @Valid @RequestBody CategoryDTO
	 * categoryDTO, BindingResult result) {
	 * 
	 * if (result.hasErrors()) { String firstErrorMessage =
	 * result.getAllErrors().stream() .map(ObjectError::getDefaultMessage)
	 * .findFirst() .orElse("Unknown validation error"); return new
	 * ResponseEntity<>(new ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST);
	 * }
	 * 
	 * if (this.categoriasServiceImpl.getCategoryModelById(id) == null) { return
	 * Utilidades.generateResponse(HttpStatus.NOT_FOUND,
	 * "Category with id: ".concat(id +" does not exist 111")); }
	 * 
	 * CategoryDTO existingCategory =
	 * this.categoriasServiceImpl.getCategoryById(id);
	 * 
	 * if(categoryDTO.getNombre() != null &&
	 * !categoryDTO.getNombre().equals(existingCategory.getNombre()) &&
	 * this.categoriasServiceImpl.existsCategoryByNameExcludingId(categoryDTO.
	 * getNombre(), id)) {
	 * 
	 * return Utilidades.generateResponse(HttpStatus.CONFLICT,
	 * "Category name already exists. Please choose another name."); }
	 * 
	 * try {
	 * 
	 * CategoriasModel categoriasModel =
	 * this.categoriasServiceImpl.getCategoryModelById(id);
	 * categoriasModel.setNombre(categoryDTO.getNombre());
	 * categoriasModel.setSlug(Utilidades.getSlug(categoriasModel.getNombre()));
	 * 
	 * this.categoriasServiceImpl.saveCategoryModel(categoriasModel); return
	 * Utilidades.generateResponse(HttpStatus.OK, "Category updated successfully.");
	 * } catch (IllegalArgumentException i) {
	 * 
	 * return Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
	 * "Category bad request: " + i.getMessage()); } catch (Exception e) {
	 * 
	 * return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
	 * "Error registering category: " + e.getMessage()); } }
	 * 
	 * @DeleteMapping("/{id}") public ResponseEntity<?>
	 * deleteCategoryById(@PathVariable("id") int id) {
	 * 
	 * if (!this.categoriasServiceImpl.existsCategoryById(id)) { return
	 * Utilidades.generateResponse(HttpStatus.NOT_FOUND,
	 * "Category with id: ".concat(id +" does not exist")); }
	 * 
	 * try { this.categoriasServiceImpl.deleteCategory(id); return
	 * Utilidades.generateResponse(HttpStatus.OK, "Category deleted successfully.");
	 * } catch (Exception e) { return
	 * Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
	 * "Error retrieving category: " + e.getMessage()); } }
	 */

	/*
	@PostMapping("/products")
	public ResponseEntity<?> createProduct(
			@Valid @RequestBody ProductosModel productModel, 
			BindingResult result,
			@RequestParam("image") MultipartFile file) throws IOException {
		
		
		if (result.hasErrors()) {
			String firstErrorMessage = result.getAllErrors().stream().map(ObjectError::getDefaultMessage).findFirst()
					.orElse("Unknown validation error");
			return new ResponseEntity<>(new ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST);
		}

		if (this.productosServiceImpl.existsProductByName(productDTO.getNombre())) {

			return Utilidades.generateResponse(HttpStatus.CONFLICT,
					"Product name already exists. Please choose another name.");
		}

		if (!this.categoriasServiceImpl.existsCategoryById(productDTO.getCategoriaId())) {

			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
					"The specified product category does not exist. Please verify the entered data.");
		}		

		try {

			this.productosServiceImpl.saveProduct(productDTO);
			return Utilidades.generateResponse(HttpStatus.CREATED, "Product created successfully.");
		} catch (Exception e) {

			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
					"Error registering category: " + e.getMessage());
		}
	}*/

}
