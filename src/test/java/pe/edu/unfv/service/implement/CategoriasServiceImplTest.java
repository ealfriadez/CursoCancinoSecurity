package pe.edu.unfv.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import pe.edu.unfv.persistence.dto.CategoryDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;
import pe.edu.unfv.persistence.repository.ICategoriasRepository;

@ExtendWith(MockitoExtension.class)
class CategoriasServiceImplTest {

	@Mock
	ICategoriasRepository iCategoriasRepository;

	@InjectMocks
	CategoriasServiceImpl categoriasServiceImpl;

	@Test
	void testGetAllCategorys() {

		// When -> Cuando
		Mockito.when(this.iCategoriasRepository.findAll()).thenReturn(DataProvider.categoriasModelListMock());
		List<CategoriasModel> result = this.categoriasServiceImpl.getAllCategorys();

		// Then -> entonces
		assertNotNull(result);
		assertFalse(result.isEmpty());
		assertEquals("Kevin De Bruyne", result.get(4).getNombre());
		assertEquals("Manchester City", result.get(4).getSlug());
		verify(this.iCategoriasRepository).findAll();
	}

	@Test
	void testSaveCategory() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		CategoryDTO categoryDTO = DataProvider.categoryDTOMock();
		CategoriasModel categoriasModel = this.categoriasServiceImpl.convertCategoryToEntity(categoryDTO);

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.save(categoriasModel)).thenReturn(DataProvider.categoriasModelMock());
		CategoryDTO result = this.categoriasServiceImpl.saveCategory(categoryDTO);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		assertEquals("Peñarol", result.getNombre());
		assertEquals("Marcador", result.getSlug());
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).save(categoriasModel);
	}

	@Test
	void testSaveCategoryModel() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		CategoriasModel categoriasModel = DataProvider.categoriasModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		this.categoriasServiceImpl.saveCategoryModel(categoriasModel);

		// Then -> entonces
		// Verificar los resultados correctamentes
		ArgumentCaptor<CategoriasModel> argumentCaptor = ArgumentCaptor.forClass(CategoriasModel.class);
		verify(this.iCategoriasRepository).save(argumentCaptor.capture());
		assertNotNull(argumentCaptor);
		assertEquals("Peñarol", argumentCaptor.getValue().getNombre());
		assertEquals("Marcador", argumentCaptor.getValue().getSlug());
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).save(categoriasModel);
	}

	@Test
	void testGetCategoryModelById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		int id = 7;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.findById(id)).thenReturn(DataProvider.categoriasOptionalModelMock());
		CategoriasModel result = this.categoriasServiceImpl.getCategoryModelById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		assertEquals("Peñarol", result.getNombre());
		assertEquals("Marcador", result.getSlug());
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).findById(id);
	}

	@Test
	void testGetCategoryModelEmptyById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		int id = 7;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.findById(id))
				.thenReturn(DataProvider.categoriasOptionalEmptyModelMock());
		CategoriasModel result = this.categoriasServiceImpl.getCategoryModelById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNull(result);
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).findById(id);
	}

	@Test
	void testGetCategoryById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		int id = 7;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.findById(id)).thenReturn(DataProvider.categoriasOptionalModelMock());
		CategoryDTO result = this.categoriasServiceImpl.getCategoryById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNotNull(result);
		assertEquals("Peñarol", result.getNombre());
		assertEquals("Marcador", result.getSlug());
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).findById(id);
	}

	@Test
	void testGetCategoryEmptyById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		int id = 7;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.findById(id))
				.thenReturn(DataProvider.categoriasOptionalEmptyModelMock());
		CategoryDTO result = this.categoriasServiceImpl.getCategoryById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertNull(result);
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).findById(id);
	}

	@Test
	void testDeleteCategory() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		int id = 7;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		this.categoriasServiceImpl.deleteCategory(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).deleteById(id);
	}

	@Test
	void testExistsCategoryById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		int id = 7;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.existsById(id)).thenReturn(true);
		boolean result = this.categoriasServiceImpl.existsCategoryById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertTrue(result);
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).existsById(id);
	}

	@Test
	void testNotExistsCategoryById() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		int id = 7;

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.existsById(id)).thenReturn(false);
		boolean result = this.categoriasServiceImpl.existsCategoryById(id);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertFalse(result);
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).existsById(id);
	}

	@Test
	void testExistsCategoryByName() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String name = "Penelope";

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.existsCategoryByNombre(name)).thenReturn(true);
		boolean result = this.categoriasServiceImpl.existsCategoryByName(name);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertTrue(result);
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).existsCategoryByNombre(name);
	}

	@Test
	void testNotExistsCategoryByName() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String name = "Penelope";

		// When -> Cuando
		// Simular el comportamiento del repositorio
		Mockito.when(this.iCategoriasRepository.existsCategoryByNombre(name)).thenReturn(false);
		boolean result = this.categoriasServiceImpl.existsCategoryByName(name);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertFalse(result);
		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.iCategoriasRepository).existsCategoryByNombre(name);
	}
	/**
	 * *
	 * 
	 * @Test void testExistsCategoryByNameExcludingId() { fail("Not yet
	 *       implemented"); }
	 * 
	 * @Test void testCategoriasServiceImpl() { fail("Not yet implemented"); }
	 */
}
