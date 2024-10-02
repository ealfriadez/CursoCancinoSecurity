package pe.edu.unfv.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import pe.edu.unfv.persistence.entity.model.ProductosModel;
import pe.edu.unfv.service.implement.CategoriasServiceImpl;
import pe.edu.unfv.service.implement.DataProvider;
import pe.edu.unfv.service.implement.ProductosServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

	@Mock
	ProductosServiceImpl productosServiceImpl;

	@Mock
	CategoriasServiceImpl categoriasServiceImpl;

	@InjectMocks
	ProductsController productsController;

	@Test
	void testPrudcts() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		List<ProductosModel> productos = DataProvider.productosModelListMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.productosServiceImpl.getAllProducts()).thenReturn(productos);
		List<ProductosModel> result = this.productsController.prudcts();

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("Neymar Jr.", result.get(2).getNombre());
		assertEquals("Virgil van Dijk", result.get(5).getNombre());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).getAllProducts();
	}

	@Test
	void testProductsByIdTrue() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.productosServiceImpl.existsProductById(id)).thenReturn(true);

		ResponseEntity<?> result = this.productsController.productsById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.OK, result.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).existsProductById(id);
	}

	@Test
	void testProductsByIdFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.productosServiceImpl.existsProductById(id)).thenReturn(false);

		ResponseEntity<?> result = this.productsController.productsById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).existsProductById(id);
	}

	@Test
	void testProductsByIdCatch() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 6;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.productosServiceImpl.existsProductById(id)).thenReturn(true);
		Mockito.doThrow(new RuntimeException("Error al obtener el producto")).when(this.productosServiceImpl)
				.getProductModelById(id);

		ResponseEntity<?> result = this.productsController.productsById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).existsProductById(id);
		verify(this.productosServiceImpl).getProductModelById(id);
	}

	@Test
	void testSaveProductUploadImage() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMock().getNombre();
		String descripcion = DataProvider.productosModelMock().getDescripcion();
		Integer precio = DataProvider.productosModelMock().getPrecio();
		Integer categoria = DataProvider.productosModelMock().getCategoriaId().getId();
		byte[] fileBytes = "datos de imagen".getBytes();
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(mockFile.getBytes()).thenReturn(fileBytes);
		Mockito.when(mockFile.getContentType()).thenReturn("image/jpeg");		
		Mockito.when(this.productosServiceImpl.existsProductByName(nombre)).thenReturn(true);
		
		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio, categoria, mockFile);
	
		// Then -> entonces
				// Verificar los resultados correctamentes
				assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testUpdateProductUploadImage() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteProductById() {
		fail("Not yet implemented");
	}

	@Test
	void testDownloadImage() {
		fail("Not yet implemented");
	}

}
