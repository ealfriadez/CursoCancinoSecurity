package pe.edu.unfv.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pe.edu.unfv.model.CategoriasModel;
import pe.edu.unfv.service.implement.CategoriasServiceImpl;
import pe.edu.unfv.util.Utilidades;


@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class BdController {

	private CategoriasServiceImpl categoriasServiceImpl;	
	
	@GetMapping("/categorias")
	public List<CategoriasModel> categorias() {
		
		return this.categoriasServiceImpl.getAllCategorys();
	}
	
	@GetMapping("/categorias/{id}")
	public CategoriasModel categorias_detalle(@PathVariable("id") Integer id) {
		
		return this.categoriasServiceImpl.getCategoryById(id);
	}
	
	@PostMapping("/categorias")
	public ResponseEntity<Object> categorias_post(@RequestBody CategoriasModel request) {
			
		request.setSlug(Utilidades.getSlug(request.getNombre()));
		this.categoriasServiceImpl.saveCategory(request);
		
		return Utilidades.generateResponse(HttpStatus.CREATED, "Se creo el registro exitosamente");
	}
	
	@PutMapping("/categorias/{id}")
	public ResponseEntity<Object> categorias_put(@PathVariable Integer id, @RequestBody CategoriasModel request) {
				
		CategoriasModel categoria = this.categoriasServiceImpl.getCategoryById(id);
		categoria.setNombre(request.getNombre());
		categoria.setSlug(Utilidades.getSlug(request.getNombre()));
		
		this.categoriasServiceImpl.saveCategory(categoria);
						
		return Utilidades.generateResponse(HttpStatus.OK, "Se edito el registro exitosamente");
	}
	
	@DeleteMapping("/categorias/{id}")
	public ResponseEntity<Object> categorias_delete(@PathVariable("id") Integer id){
		
		CategoriasModel categoria = this.categoriasServiceImpl.getCategoryById(1);			
		try {	
			
			if(categoria==null) {
				return Utilidades.generateResponse(HttpStatus.FORBIDDEN, "No está autorizado a realizar esta accion, el registro a eliminar esta relacionado con otros registros o no existe.");
			}
			
			this.categoriasServiceImpl.deleteCategory(id);
			return Utilidades.generateResponse(HttpStatus.OK, "Se elimino el registro exitosamente");
			
		} catch (Exception e) {			
			return Utilidades.generateResponse(HttpStatus.BAD_REQUEST, "Fallo la ejecucion, por favor vuelva a intentarlo mas tarde.");
		}
	}

}
