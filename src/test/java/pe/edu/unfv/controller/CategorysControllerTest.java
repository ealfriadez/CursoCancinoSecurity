package pe.edu.unfv.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
		Mockito.doThrow(new RuntimeException("Error al obtener categoría")).when(this.categoriasServiceImpl)
				.getCategoryById(id);

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

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(this.categoriasServiceImpl.existsCategoryByName(anyString())).thenReturn(false);

		ResponseEntity<?> response = this.categorysController.createCategory(DataProvider.categoryDTOMock(),
				mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryByName(anyString());
	}

	@Test
	void testCreateCategoryHasError() {

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
		Mockito.when(mockBindingResult.getAllErrors())
				.thenReturn(Collections.singletonList(new FieldError("categoryDTO", "nombre", "Invalid name")));

		ResponseEntity<?> response = this.categorysController.createCategory(DataProvider.categoryDTOMock(),
				mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testCreateCategoryDuplicateName() {

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(this.categoriasServiceImpl.existsCategoryByName(anyString())).thenReturn(true);

		ResponseEntity<?> response = this.categorysController.createCategory(DataProvider.categoryDTOMock(),
				mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryByName(anyString());
	}

	@Test
	void testCreateCategoryTryCatch() {

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(this.categoriasServiceImpl.existsCategoryByName(anyString())).thenReturn(false);
		Mockito.doThrow(new RuntimeException("Error al crear categoría")).when(this.categoriasServiceImpl)
				.saveCategory(DataProvider.categoryDTOMock());

		ResponseEntity<?> response = this.categorysController.createCategory(DataProvider.categoryDTOMock(),
				mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryByName(anyString());
		verify(this.categoriasServiceImpl).saveCategory(DataProvider.categoryDTOMock());
	}

	@Test
	void testUpdateCategoryHasErrorsTrue() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);
		CategoryDTO categoryDTO = this.categoriasServiceImpl.getCategoryById(id);

		ResponseEntity<?> response = this.categorysController.updateCategory(id, categoryDTO, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).getCategoryById(id);
	}

	@Test
	void testUpdateCategoryHasErrorsFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;
		CategoryDTO categoryDTO = DataProvider.categoryDTOMock(); // Create a sample category DTO
		CategoriasModel categoriasModel = DataProvider.categoriasModelMock(); // Create a sample category Model

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(categoriasServiceImpl.getCategoryModelById(id)).thenReturn(categoriasModel);
		Mockito.when(categoriasServiceImpl.getCategoryById(id)).thenReturn(categoryDTO);

		ResponseEntity<?> response = this.categorysController.updateCategory(id, categoryDTO, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).getCategoryById(id);
	}

	@Test
	void testUpdateCategoryCategoryModelByIdNull() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;
		CategoryDTO categoryDTO = DataProvider.categoryDTOMock(); // Create a sample category DTO
		CategoriasModel categoriasModel = DataProvider.categoryModelNullMock(); // Create a sample category Model

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(categoriasServiceImpl.getCategoryModelById(id)).thenReturn(categoriasModel);

		ResponseEntity<?> response = this.categorysController.updateCategory(id, categoryDTO, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).getCategoryModelById(id);
	}

	/*
	 * @Test void testUpdateExistsCategoryByNameExcludingId() {
	 * 
	 * // Given -> Mientras // Convertir para el comportamiento esperado Integer id
	 * = 6; CategoryDTO categoryDTO = DataProvider.categoryDTOMock(); // Create a
	 * sample category DTO CategoriasModel categoriasModel =
	 * DataProvider.categoriasModelMock(); // Create a sample category Model
	 * CategoryDTO existingCategory = DataProvider.categoryDTOMock();
	 * 
	 * // Given -> Mientras // Convertir para el comportamiento esperado
	 * BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
	 * Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
	 * 
	 * // Mockito.when(categoriasServiceImpl.getCategoryModelById(id)).thenReturn(
	 * categoriasModel);
	 * Mockito.when(categoriasServiceImpl.getCategoryById(id)).thenReturn(
	 * existingCategory);
	 * Mockito.when(categoriasServiceImpl.existsCategoryByNameExcludingId(
	 * categoryDTO.getNombre(), id)) .thenReturn(true);
	 * 
	 * // Act ResponseEntity<?> response =
	 * this.categorysController.updateCategory(id, categoryDTO, mockBindingResult);
	 * 
	 * // Assert assertEquals(HttpStatus.OK, response.getStatusCode()); }
	 * 
	 * @Test void testUpdateDemoExistsCategoryByNameExcludingId() {
	 * 
	 * // Arrange Integer id = 6;
	 * 
	 * CategoryDTO categoryDTO = DataProvider.categoryDTOMock(); // Create a sample
	 * category DTO CategoriasModel categoriasModel =
	 * DataProvider.categoriasModelMock(); // Create a sample category Model
	 * CategoryDTO existingCategory = new CategoryDTO();
	 * existingCategory.setNombre("xxxx");
	 * 
	 * BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
	 * Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
	 * 
	 * // Mockito.when(categoriasServiceImpl.getCategoryModelById(id)).thenReturn(
	 * categoriasModel); //
	 * Mockito.when(categoriasServiceImpl.getCategoryById(id)).thenReturn(
	 * existingCategory); //
	 * Mockito.when(categoriasServiceImpl.existsCategoryByNameExcludingId(
	 * categoryDTO.getNombre(), // id)).thenReturn(true);
	 * 
	 * //
	 * Mockito.when(categoriasServiceImpl.getCategoryModelById(id)).thenReturn(new
	 * // CategoriasModel()); //
	 * Mockito.when(categoriasServiceImpl.getCategoryById(id)).thenReturn(
	 * existingCategory); //
	 * Mockito.when(!categoryDTO.getNombre().equals(existingCategory.getNombre())).
	 * thenReturn(true); //
	 * Mockito.when(categoriasServiceImpl.existsCategoryByNameExcludingId(
	 * categoryDTO.getNombre(), // id)).thenReturn(true);
	 * 
	 * int categoryId = 1; CategoryDTO categoryDTO1 = new CategoryDTO("Old Name",
	 * "xx"); CategoryDTO existingCategory1 = new CategoryDTO("Old Name", "xx"); //
	 * Nombre duplicado
	 * Mockito.when(categoriasServiceImpl.getCategoryModelById(categoryId))
	 * .thenReturn(new CategoriasModel(categoryId, "Old Name", "xx"));
	 * Mockito.when(categoriasServiceImpl.getCategoryById(categoryId)).thenReturn(
	 * existingCategory1);
	 * Mockito.when(categoriasServiceImpl.existsCategoryByNameExcludingId(
	 * categoryDTO1.getNombre(), categoryId)) .thenReturn(true);
	 * 
	 * // Act ResponseEntity<?> response =
	 * this.categorysController.updateCategoryDemo(categoryId, categoryDTO1,
	 * mockBindingResult);
	 * 
	 * // Assert assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()); }
	 */

	@Test
	void testUpdateCategorySaveCategoryModelCathIllegalArgumentException() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;
		CategoryDTO categoryDTO = DataProvider.categoryDTOMock(); // Create a sample category DTO
		CategoriasModel categoriasModel = DataProvider.categoriasModelMock(); // Create a sample category Model

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(categoriasServiceImpl.getCategoryModelById(id)).thenReturn(categoriasModel);
		Mockito.when(categoriasServiceImpl.getCategoryById(id)).thenReturn(categoryDTO);
		Mockito.doThrow(new IllegalArgumentException("Error retrieving category")).when(this.categoriasServiceImpl)
				.saveCategoryModel(categoriasModel);

		ResponseEntity<?> response = this.categorysController.updateCategory(id, categoryDTO, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).getCategoryById(id);
		verify(this.categoriasServiceImpl).saveCategoryModel(categoriasModel);
	}

	@Test
	void testUpdateCategorySaveCategoryModelCathException() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		CategoryDTO categoryDTO = DataProvider.categoryDTOMock(); // Create a sample category DTO
		CategoriasModel categoriasModel = DataProvider.categoriasModelMock(); // Create a sample category Model

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(categoriasServiceImpl.getCategoryModelById(id)).thenReturn(categoriasModel);
		Mockito.when(categoriasServiceImpl.getCategoryById(id)).thenReturn(categoryDTO);
		Mockito.doThrow(new RuntimeException("Error retrieving category")).when(this.categoriasServiceImpl)
				.saveCategoryModel(categoriasModel);

		ResponseEntity<?> response = this.categorysController.updateCategory(id, categoryDTO, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).getCategoryById(id);
		verify(this.categoriasServiceImpl).saveCategoryModel(categoriasModel);
	}

	@Test
	void testdeleteCategoryByIdFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(categoriasServiceImpl.existsCategoryById(id)).thenReturn(false);

		ResponseEntity<?> response = this.categorysController.deleteCategoryById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(id);
	}

	@Test
	void testdeleteCategoryByIdTrueCatch() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(categoriasServiceImpl.existsCategoryById(id)).thenReturn(true);
		Mockito.doThrow(new RuntimeException("Error retrieving category")).when(this.categoriasServiceImpl)
				.deleteCategory(id);

		ResponseEntity<?> response = this.categorysController.deleteCategoryById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(id);
		verify(this.categoriasServiceImpl).deleteCategory(id);
	}

	@Test
	void testdeleteCategoryByIdTrueTry() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		CategoriasModel categoriasModel = DataProvider.categoriasModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(categoriasServiceImpl.existsCategoryById(categoriasModel.getId())).thenReturn(true);

		ResponseEntity<?> response = this.categorysController.deleteCategoryById(categoriasModel.getId());

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.OK, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoriasModel.getId());
		verify(this.categoriasServiceImpl).deleteCategory(categoriasModel.getId());
	}
}
