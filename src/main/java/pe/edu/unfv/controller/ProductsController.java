package pe.edu.unfv.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.edu.unfv.controller.dto.ResponseNew;
import pe.edu.unfv.persistence.dto.ProductDTO;
import pe.edu.unfv.persistence.entity.model.ImageDataModel;
import pe.edu.unfv.persistence.entity.model.ProductosModel;
import pe.edu.unfv.service.implement.CategoriasServiceImpl;
import pe.edu.unfv.service.implement.ImageDataServiceImpl;
import pe.edu.unfv.service.implement.ProductosServiceImpl;
import pe.edu.unfv.util.Constantes;
import pe.edu.unfv.util.Utilidades;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class ProductsController {

	private ProductosServiceImpl productosServiceImpl;
	private CategoriasServiceImpl categoriasServiceImpl;
	private ImageDataServiceImpl imageDataServiceImpl;

	// @Value("${elio.valores.ruta_upload}")
	// private String ruta_upload;

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
	public ResponseEntity<?> uploadImage(@Valid @RequestParam("image") MultipartFile file) throws IOException {
		
		ImageDataModel imageDataModel = this.imageDataServiceImpl.uploadImage(file);

		if (imageDataModel == null) {
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "Image not upload exists errors.");
		}

		return Utilidades.generateResponse(HttpStatus.CREATED, "Image upload successfully.");
	}

	/*
	 * @PostMapping(value = "/products", consumes = {"multipart/form-data"}) public
	 * ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO,
	 * BindingResult result, @RequestParam("file") MultipartFile file){
	 * 
	 * if (result.hasErrors()) { String firstErrorMessage =
	 * result.getAllErrors().stream() .map(ObjectError::getDefaultMessage)
	 * .findFirst() .orElse("Unknown validation error"); return new
	 * ResponseEntity<>(new ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST);
	 * }
	 * 
	 * if (this.productosServiceImpl.existsProductByName(productDTO.getNombre())) {
	 * 
	 * return Utilidades.generateResponse(HttpStatus.CONFLICT,
	 * "Product name already exists. Please choose another name."); }
	 * 
	 * if(!this.categoriasServiceImpl.existsCategoryById(productDTO.getCategoriaId()
	 * )) {
	 * 
	 * return Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
	 * "The specified product category does not exist. Please verify the entered data."
	 * ); }
	 * 
	 * if(!file.isEmpty()) { HttpStatus status = HttpStatus.OK; String mensaje ="";
	 * 
	 * String nombreImagen = Utilidades.guardarArchivo(file, Constantes.RUTA_UPLOAD
	 * +"producto/"); if(nombreImagen=="no") { return
	 * Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
	 * "La foto enviada no es válida, debe ser JPG|PNG."); }else {
	 * if(nombreImagen!=null) { //producto.setFoto(nombreImagen);
	 * 
	 * //this.productosServiceImpl.saveProduct(producto);
	 * 
	 * return Utilidades.generateResponse(HttpStatus.CREATED,
	 * "Product created successfully."); } } }else { return
	 * Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
	 * "La foto enviada no es válida, debe ser JPG|PNG."); }
	 * 
	 * 
	 *//**********************
		* 
		*//*
			 * 
			 * try {
			 * 
			 * //String nombreImagen = Utilidades.guardarArchivo(file, this.ruta_upload
			 * +"producto2/");
			 * 
			 * 
			 * this.productosServiceImpl.saveProduct(productDTO); return
			 * Utilidades.generateResponse(HttpStatus.CREATED,
			 * "Product created successfully."); } catch (Exception e) {
			 * 
			 * return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
			 * "Error registering category: " + e.getMessage()); } }
			 */
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
	 * @PostMapping("/products") public ResponseEntity<?>
	 * createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult
	 * result, @RequestParam("file") MultipartFile file){ if (result.hasErrors()) {
	 * String firstErrorMessage = result.getAllErrors().stream()
	 * .map(ObjectError::getDefaultMessage) .findFirst()
	 * .orElse("Unknown validation error"); return new ResponseEntity<>(new
	 * ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST); }
	 * 
	 * if (this.productosServiceImpl.existsProductByName(productDTO.getNombre())) {
	 * 
	 * return Utilidades.generateResponse(HttpStatus.CONFLICT,
	 * "Product name already exists. Please choose another name."); }
	 * 
	 * if(!this.categoriasServiceImpl.existsCategoryById(productDTO.getCategoriaId()
	 * )) {
	 * 
	 * return Utilidades.generateResponse(HttpStatus.BAD_REQUEST,
	 * "The specified product category does not exist. Please verify the entered data."
	 * ); }
	 * 
	 * if(!file.isEmpty()) { HttpStatus status = HttpStatus.OK; String mensaje ="";
	 * 
	 * String nombreImagen = Utilidades.guardarArchivo(file, this.ruta_upload
	 * +"producto2/"); if(nombreImagen=="no") { status = HttpStatus.BAD_REQUEST;
	 * mensaje ="La foto enviada no es válida, debe ser JPG|PNG"; }else {
	 * if(nombreImagen!=null) { producto.setFoto(nombreImagen);
	 * 
	 * this.productosServiceImpl.saveProduct(producto);
	 * 
	 * status= HttpStatus.CREATED; mensaje="Se creó el registro exitosamente"; } }
	 * }else { status = HttpStatus.BAD_REQUEST; mensaje
	 * ="La foto enviada no es válida, debe ser JPG|PNG"; }
	 * 
	 * 
	 * 
	 * 
	 * try {
	 * 
	 * this.productosServiceImpl.saveProduct(productDTO); return
	 * Utilidades.generateResponse(HttpStatus.CREATED,
	 * "Product created successfully."); } catch (Exception e) {
	 * 
	 * return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR,
	 * "Error registering category: " + e.getMessage()); } }
	 */
}
