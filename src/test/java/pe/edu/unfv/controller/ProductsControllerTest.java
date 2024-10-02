package pe.edu.unfv.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
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
import pe.edu.unfv.util.Utilidades;

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
	void testSaveProductUploadImageExistsProductByNameTrue() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.productosServiceImpl.existsProductByName(nombre)).thenReturn(true);

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).existsProductByName(nombre);
	}

	@Test
	void testSaveProductUploadImageExistsProductByNameFalse() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.productosServiceImpl.existsProductByName(nombre)).thenReturn(false);

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).existsProductByName(nombre);
	}

	@Test
	void testSaveProductUploadImageEmptyFields() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado		
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		ResponseEntity<?> response = productsController.saveProductUploadImage("", "Description", 100, 1, mockFile);
		ResponseEntity<?> response1 = productsController.saveProductUploadImage("Description", "", 100, 1, mockFile);
		ResponseEntity<?> response2 = productsController.saveProductUploadImage("Description", "Description", 0, 1,
				mockFile);
		ResponseEntity<?> response3 = productsController.saveProductUploadImage("Description", "Description", 1, 0,
				mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
	}

	@Test
	void testSaveProductUploadImageExistsCategoryByIdFalse() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(false);

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoria);
	}

	@Test
	void testSaveProductUploadImageExistsCategoryByIdTrue() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		MultipartFile mockFile = mock(MultipartFile.class);
		Mockito.when(mockFile.getContentType()).thenReturn("image/jpeg");
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(true);

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoria);
	}

	@Test
	void testSaveProductUploadImageFileIsEmptyTrue() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		MultipartFile mockFile = mock(MultipartFile.class);
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(true);
		Mockito.when(mockFile.isEmpty()).thenReturn(true);

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoria);
	}

	@Test
	void testSaveProductUploadImageFileIsEmptyFalse() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		MultipartFile mockFile = mock(MultipartFile.class);
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(true);
		Mockito.when(mockFile.getContentType()).thenReturn("image/jpeg");
		Mockito.when(mockFile.isEmpty()).thenReturn(false);

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoria);
	}

	@Test
	void testSaveProductUploadImageNombreImagenNo() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		MultipartFile mockFile = mock(MultipartFile.class);
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(true);
		Mockito.when(mockFile.getContentType()).thenReturn("image/jpeg");
		Mockito.when(mockFile.isEmpty()).thenReturn(false);

		String nombreImagen = Utilidades.guardarArchivo(mockFile, null);
		Mockito.when(nombreImagen).thenReturn("no");

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoria);
	}

	@Test
	void testSaveProductUploadImageNombreImagenDiferenteNo() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		MultipartFile mockFile = mock(MultipartFile.class);
		// Mockito.when(mockFile.getBytes()).thenReturn(fileBytes);
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(true);
		Mockito.when(mockFile.getContentType()).thenReturn("image/jpeg");
		Mockito.when(mockFile.isEmpty()).thenReturn(false);

		String nombreImagen = Utilidades.guardarArchivo(mockFile, null);
		Mockito.when(nombreImagen).thenReturn("xx");

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoria);
	}

	@Test
	void testSaveProductUploadImageNombreImagenCatch() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		MultipartFile mockFile = mock(MultipartFile.class);
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(true);
		Mockito.when(mockFile.getContentType()).thenReturn("image/jpeg");
		Mockito.when(mockFile.isEmpty()).thenReturn(false);
		Mockito.doThrow(new RuntimeException("Upload failed")).when(productosServiceImpl).saveProductImage(anyString(),
				anyString(), anyInt(), anyInt(), any(), anyString());

		ResponseEntity<?> response = this.productsController.saveProductUploadImage(nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).saveProductImage(anyString(), anyString(), anyInt(), anyInt(), any(),
				anyString());
	}
	
	@Test
	void testUpdateProductUploadImageeExistsProductByNameTrue() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = DataProvider.productosModelMockCategory().getId();
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.productosServiceImpl.existsProductByName(nombre)).thenReturn(true);

		ResponseEntity<?> response = this.productsController.updateProductUploadImage(id, nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).existsProductByName(nombre);
	}
	
	@Test
	void testUpdateProductUploadImageeExistsProductByNameFalse() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = DataProvider.productosModelMockCategory().getId();
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.productosServiceImpl.existsProductByName(nombre)).thenReturn(false);

		ResponseEntity<?> response = this.productsController.updateProductUploadImage(id, nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.productosServiceImpl).existsProductByName(nombre);
	}
	
	@Test
	void testUpdateProductUploadImageEmptyFields() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado	
		Integer id = DataProvider.productosModelMockCategory().getId();
		MultipartFile mockFile = mock(MultipartFile.class);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		ResponseEntity<?> response = productsController.updateProductUploadImage(id, "", "Description", 100, 1, mockFile);
		ResponseEntity<?> response1 = productsController.updateProductUploadImage(id, "Description", "", 100, 1, mockFile);
		ResponseEntity<?> response2 = productsController.updateProductUploadImage(id, "Description", "Description", 0, 1,
				mockFile);
		ResponseEntity<?> response3 = productsController.updateProductUploadImage(id, "Description", "Description", 1, 0,
				mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
		assertEquals(HttpStatus.BAD_REQUEST, response3.getStatusCode());
	}
	
	@Test
	void testUpdateProductUploadImageExistsCategoryByIdFalse() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = DataProvider.productosModelMockCategory().getId();
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();
		MultipartFile mockFile = mock(MultipartFile.class);		
		
		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(!this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(false);

		ResponseEntity<?> response = this.productsController.updateProductUploadImage(id, nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoria);
	}	
	
	@Test
	void testUpdateProductUploadImageFileIsEmptyTrue() throws IOException {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = DataProvider.productosModelMockCategory().getId();
		String nombre = DataProvider.productosModelMockCategory().getNombre();
		String descripcion = DataProvider.productosModelMockCategory().getDescripcion();
		Integer precio = DataProvider.productosModelMockCategory().getPrecio();
		Integer categoria = DataProvider.productosModelMockCategory().getId();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		MultipartFile mockFile = mock(MultipartFile.class);
		Mockito.when(this.categoriasServiceImpl.existsCategoryById(categoria)).thenReturn(true);
		Mockito.when(mockFile.isEmpty()).thenReturn(true);

		ResponseEntity<?> response = this.productsController.updateProductUploadImage(id, nombre, descripcion, precio,
				categoria, mockFile);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.categoriasServiceImpl).existsCategoryById(categoria);
	}
	
	/*
	@Test
	void testDeleteProductById() {
		fail("Not yet implemented");
	}

	@Test
	void testDownloadImage() {
		fail("Not yet implemented");
	}
*/
}
