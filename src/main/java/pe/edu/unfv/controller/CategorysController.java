package pe.edu.unfv.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pe.edu.unfv.controller.dto.ResponseNew;
import pe.edu.unfv.persistence.dto.CategoryDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;
import pe.edu.unfv.service.implement.CategoriasServiceImpl;
import pe.edu.unfv.util.Utilidades;

@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class CategorysController {

	private CategoriasServiceImpl categoriasServiceImpl;
	
	@GetMapping("/categorys")
	public List<CategoriasModel> categorias(){
		
		return this.categoriasServiceImpl.getAllCategorys();
	}
	
	@GetMapping("/categorys/{id}")
	public ResponseEntity<?>  categorysById(@PathVariable("id") Integer id) {
		
		if (!this.categoriasServiceImpl.existsCategoryById(id)) {			
			return Utilidades.generateResponse(HttpStatus.NOT_FOUND, 
					"Category with id: ".concat(id +" does not exist"));
		}
		
		try {
			CategoryDTO categoryDTO = this.categoriasServiceImpl.getCategoryById(id);
			return ResponseEntity.ok(categoryDTO);
		} catch (final Exception e) {
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
					"Error retrieving category: " + e.getMessage());			
		}		
	}
	
	@PostMapping("/")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDTO categoryDTO, BindingResult result){		
		if (result.hasErrors()) {
            String firstErrorMessage = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown validation error");
            return new ResponseEntity<>(new ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST);
        }		
		
		if (this.categoriasServiceImpl.existsCategoryByName(categoryDTO.getNombre())) {
            
            return Utilidades.generateResponse(HttpStatus.CONFLICT, 
            		"Category name already exists. Please choose another name.");
        }
		
		try {
			
			this.categoriasServiceImpl.saveCategory(categoryDTO);			
			return Utilidades.generateResponse(HttpStatus.CREATED, "Category created successfully.");
		} catch (Exception e) {
			
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
					"Error registering category: " + e.getMessage());
		}		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategory(@PathVariable("id") int id, 
			@Valid @RequestBody CategoryDTO categoryDTO, 
			BindingResult result) {
		
		if (result.hasErrors()) {
            String firstErrorMessage = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown validation error");
            return new ResponseEntity<>(new ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST);
        }
		
		if (this.categoriasServiceImpl.getCategoryModelById(id) == null) {
			return Utilidades.generateResponse(HttpStatus.NOT_FOUND, 
					"Category with id: ".concat(id +" does not exist"));
		}		
		
		CategoryDTO existingCategory = this.categoriasServiceImpl.getCategoryById(id);
		
		if(categoryDTO.getNombre() != null 
				&& 
				!categoryDTO.getNombre().equals(existingCategory.getNombre()) 
				&& 
				this.categoriasServiceImpl.existsCategoryByNameExcludingId(categoryDTO.getNombre(), id)) {
			
			return Utilidades.generateResponse(HttpStatus.CONFLICT, 
					"Category name already exists. Please choose another name."); 
		}
		
		try {
			
			CategoriasModel categoriasModel = this.categoriasServiceImpl.getCategoryModelById(id);
			categoriasModel.setNombre(categoryDTO.getNombre());
			categoriasModel.setSlug(Utilidades.getSlug(categoriasModel.getNombre()));
			
			this.categoriasServiceImpl.saveCategoryModel(categoriasModel);		
			return Utilidades.generateResponse(HttpStatus.OK, "Category updated successfully.");
		} catch (IllegalArgumentException i) {
			
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "Category bad request: " + i.getMessage());
		} catch (Exception e) {
			
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
					"Error registering category: " + e.getMessage());
		}		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateCategoryDemo(@PathVariable("id") int id, 
			@Valid @RequestBody CategoryDTO categoryDTO, 
			BindingResult result) {
		
		if (result.hasErrors()) {
            String firstErrorMessage = result.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .findFirst()
                .orElse("Unknown validation error");
            return new ResponseEntity<>(new ResponseNew(firstErrorMessage), HttpStatus.BAD_REQUEST);
        }
		
		if (this.categoriasServiceImpl.getCategoryModelById(id) == null) {
			return Utilidades.generateResponse(HttpStatus.NOT_FOUND, 
					"Category with id: ".concat(id +" does not exist"));
		}		
		
		CategoryDTO existingCategory = this.categoriasServiceImpl.getCategoryById(id);
		
		if(categoryDTO.getNombre() != null && 
				!categoryDTO.getNombre().equals(existingCategory.getNombre()) && 
					this.categoriasServiceImpl.existsCategoryByNameExcludingId(categoryDTO.getNombre(), id)) {
		//if(this.categoriasServiceImpl.existsCategoryByNameExcludingId(categoryDTO.getNombre(), id)) {
		//if(categoryDTO.getNombre() != null) {
		//if(!categoryDTO.getNombre().equals(existingCategory.getNombre())) {
			return Utilidades.generateResponse(HttpStatus.CONFLICT, 
					"Category name already exists. Please choose another name."); 
		}
		
		try {
			
			CategoriasModel categoriasModel = this.categoriasServiceImpl.getCategoryModelById(id);
			categoriasModel.setNombre(categoryDTO.getNombre());
			categoriasModel.setSlug(Utilidades.getSlug(categoriasModel.getNombre()));
			
			this.categoriasServiceImpl.saveCategoryModel(categoriasModel);		
			return Utilidades.generateResponse(HttpStatus.OK, "Category updated successfully.");
		} catch (IllegalArgumentException i) {
			
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "Category bad request: " + i.getMessage());
		} catch (Exception e) {
			
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
					"Error registering category: " + e.getMessage());
		}		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?>  deleteCategoryById(@PathVariable("id") int id) {
		
		if (!this.categoriasServiceImpl.existsCategoryById(id)) {			
			return Utilidades.generateResponse(HttpStatus.NOT_FOUND, 
					"Category with id: ".concat(id +" does not exist"));
		}
		
		try {
			this.categoriasServiceImpl.deleteCategory(id);
			return Utilidades.generateResponse(HttpStatus.OK, "Category deleted successfully.");
		} catch (Exception e) {
			return Utilidades.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, 
					"Error retrieving category: " + e.getMessage());			
		}		
	}
}
