package pe.edu.unfv.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.List;

import javax.naming.Binding;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import pe.edu.unfv.controller.dto.ResponseNew;
import pe.edu.unfv.persistence.dto.CategoryDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;
import pe.edu.unfv.service.implement.CategoriasServiceImpl;
import pe.edu.unfv.service.implement.DataProvider;

@ExtendWith(MockitoExtension.class)
class CategorysControllerTest {

	@Mock
	CategoriasServiceImpl categoriasServiceImpl;

	@InjectMocks
	CategorysController categorysController;

	@Test
	void testCategorias() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		List<CategoriasModel> categorias = DataProvider.categoriasModelListMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.categoriasServiceImpl.getAllCategorys()).thenReturn(categorias);
		List<CategoriasModel> result = this.categorysController.categorias();

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("Neymar Jr.", result.get(2).getNombre());
		assertEquals("Virgil van Dijk", result.get(5).getNombre());
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).getAllCategorys();
	}

	@Test
	void testNotCategorysById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 5;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(id)).thenReturn(false);
		ResponseEntity<?> result = this.categorysController.categorysById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes

		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		// assertEquals("Category with id: ".concat(id +" does not exist"),
		// result.getStatusCode().value());
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(id);
	}

	@Test
	void testCategorysById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(id)).thenReturn(true);		
		ResponseEntity<?> result = this.categorysController.categorysById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.OK, result.getStatusCode());
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(id);
	}
	
	@Test
	void testCategorysByIdTry() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(id)).thenReturn(true);	
		//Mockito.when(this.categoriasServiceImpl.getCategoryById(id)).thenReturn(DataProvider.categoryDTOMock());	
		ResponseEntity<?> result = this.categorysController.categorysById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(id);
		verify(this.categoriasServiceImpl).getCategoryById(id);
	}
	
	@Test
	void testCategorysByIdCatch() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(id)).thenReturn(true);			
		Mockito.doThrow(new RuntimeException("Error al obtener categoría")).when(this.categoriasServiceImpl).getCategoryById(id);
		ResponseEntity<?> result = this.categorysController.categorysById(id);
		
		// Then -> entonces
		// Verificar los resultados correctamentes		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
		
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(id);
        verify(this.categoriasServiceImpl).getCategoryById(id);        
	}
	
	@Test
	void testCreateCategory() {

		// Arrange
	   // CategoryDTO validCategoryDTO = new CategoryDTO();
	    //validCategoryDTO.setNombre("ValidCategory");
	    
	    BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
	    
	    Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
	    Mockito.when(this.categoriasServiceImpl.existsCategoryByName(anyString())).thenReturn(false);

	    // Act
	    ResponseEntity<?> response = this.categorysController.createCategory(DataProvider.categoryDTOMock(), mockBindingResult);

	    // Assert
	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	void testCreateCategoryHasError() {

		// Arrange	    
	    BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
	    
	    Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
	    Mockito.when(this.categoriasServiceImpl.existsCategoryByName(anyString())).thenReturn(false);

	    // Act
	    ResponseEntity<?> response = this.categorysController.createCategory(DataProvider.categoryDTOMock(), mockBindingResult);

	    // Assert
	    assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
}
