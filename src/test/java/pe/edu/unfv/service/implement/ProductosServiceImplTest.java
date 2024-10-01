package pe.edu.unfv.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import pe.edu.unfv.persistence.dto.ProductDTO;
import pe.edu.unfv.persistence.entity.model.ProductosModel;
import pe.edu.unfv.persistence.repository.IProductosRepository;

@ExtendWith(MockitoExtension.class)
class ProductosServiceImplTest {

	@Mock
	IProductosRepository iProductosRepository;

	@InjectMocks
	ProductosServiceImpl productosServiceImpl;

	@Test
	void testGetAllProducts() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		List<ProductosModel> productos = DataProvider.productosModelListMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.findAll(Sort.by("id").descending())).thenReturn(productos);
		List<ProductosModel> result = this.productosServiceImpl.getAllProducts();

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("Kevin De Bruyne", result.get(4).getNombre());
		assertEquals("Manchester City", result.get(4).getSlug());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).findAll(Sort.by("id").descending());
	}

	@Test
	void testGetProductDTOByIdTrue() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 1;
		Optional<ProductosModel> ProductosModel = DataProvider.productsOptionalModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.findById(id)).thenReturn(ProductosModel);
		ProductDTO result = this.productosServiceImpl.getProductDTOById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		// assertFalse(result.);
		assertEquals("Lionel Messi", result.getNombre());
		assertEquals("Jugado 1", result.getDescripcion());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).findById(id);
	}

	@Test
	void testGetProductDTOByIdFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 1;
		Optional<ProductosModel> ProductosModel = DataProvider.productsOptionalModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.findById(id)).thenReturn(ProductosModel);
		ProductDTO result = this.productosServiceImpl.getProductDTOById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		// assertFalse(result.);
		assertEquals("Lionel Messi", result.getNombre());
		assertEquals("Jugado 1", result.getDescripcion());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).findById(id);
	}

	@Test
	void testGetProductModelByIdTrue() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 1;
		Optional<ProductosModel> ProductosModel = DataProvider.productsOptionalModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.findById(id)).thenReturn(ProductosModel);
		ProductosModel result = this.productosServiceImpl.getProductModelById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		// assertFalse(result.);
		assertEquals("Lionel Messi", result.getNombre());
		assertEquals("Jugado 1", result.getDescripcion());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).findById(id);

	}

	@Test
	void testGetProductModelByIdFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 1;
		Optional<ProductosModel> productosModel = DataProvider.productsOptionalModelEmptyModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.findById(id)).thenReturn(productosModel);
		ProductosModel result = this.productosServiceImpl.getProductModelById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNull(result);

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).findById(id);

	}

	@Test
	void testExistsProductByNameTrue() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Optional<ProductosModel> productosModel = DataProvider.productsOptionalModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.existsProductByNombre(productosModel.get().getNombre()))
				.thenReturn(true);

		Boolean result = this.productosServiceImpl.existsProductByName(productosModel.get().getNombre());

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertTrue(result);

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).existsProductByNombre(productosModel.get().getNombre());
	}

	@Test
	void testExistsProductByNameFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Optional<ProductosModel> productosModel = DataProvider.productsOptionalModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.existsProductByNombre(productosModel.get().getNombre()))
				.thenReturn(false);

		Boolean result = this.productosServiceImpl.existsProductByName(productosModel.get().getNombre());

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertFalse(result);

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).existsProductByNombre(productosModel.get().getNombre());
	}

	@Test
	void testSaveProductoModelTrue() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		ProductosModel productosModelMock = DataProvider.productosModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		this.productosServiceImpl.saveProductoModel(productosModelMock);

		// Then -> entonces
		// Verificar los resultados correctamentes
		ArgumentCaptor<ProductosModel> argumentCaptor = ArgumentCaptor.forClass(ProductosModel.class);
		verify(this.iProductosRepository).save(argumentCaptor.capture());
		assertNotNull(argumentCaptor);
		assertEquals("Lionel Messi", argumentCaptor.getValue().getNombre());
		assertEquals("Inter Miami", argumentCaptor.getValue().getSlug());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).save(productosModelMock);
	}

	@Test
	void testSaveProductoModelFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		ProductosModel productosModelEmptyMock = DataProvider.productosModelEmptyMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		this.productosServiceImpl.saveProductoModel(productosModelEmptyMock);

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).save(productosModelEmptyMock);
	}

	@Test
	void testSaveProduct() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		ProductDTO productDTOMock = DataProvider.productDTOMock();
		ProductosModel productosModel = this.productosServiceImpl.convertDTOtoProduct(productDTOMock);
		ProductosModel productosModelNew = DataProvider.productosModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.save(productosModel)).thenReturn(productosModelNew);
		ProductDTO result = this.productosServiceImpl.saveProduct(productDTOMock);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		assertEquals("Lionel Messi", result.getNombre());
		assertEquals("Jugado 1", result.getDescripcion());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).save(productosModel);
	}

	@Test
	void testDeleteProduct() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 1;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		this.productosServiceImpl.deleteProduct(id);

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).deleteById(id);
	}

	@Test
	void testExistsProductById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		Integer id = 1;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iProductosRepository.existsById(id)).thenReturn(true);
		boolean result = this.productosServiceImpl.existsProductById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertTrue(result);
		
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iProductosRepository).existsById(id);

	}
	/*
	 * 
	 * @Test void testSaveProductImage() { fail("Not yet implemented"); }
	 * 
	 * @Test void testDownloadImage() { fail("Not yet implemented"); }
	 */
}
