package pe.edu.unfv.service.implement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
	
	private CategoriasServiceImpl serviceImpl;

	@Test
	void testGetAllCategorys() {

		// When -> Cuando
		Mockito.when(iCategoriasRepository.findAll()).thenReturn(DataProvider.categoriasModelListMock());
		List<CategoriasModel> result = categoriasServiceImpl.getAllCategorys();

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
		CategoryDTO categoryDTO = DataProvider.categoryDTOMock();		
		//CategoriasModel categoriasModel = serviceImpl.convertCategoryToEntity(categoryDTO);	
		
		// When -> Cuando
		//Mockito.when(iCategoriasRepository.save(categoriasModel)).thenReturn(DataProvider.newCategoriasModelMock());
		CategoryDTO result = categoriasServiceImpl.saveCategory(categoryDTO);

		// Then -> entonces
		ArgumentCaptor<CategoryDTO> argumentCaptor = ArgumentCaptor.forClass(CategoryDTO.class);
		verify(this.categoriasServiceImpl).saveCategory(argumentCaptor.capture());
		assertNotNull(result);
		/*
		assertFalse(result.isEmpty());
		assertEquals("Kevin De Bruyne", result.get(4).getNombre());
		assertEquals("Manchester City", result.get(4).getSlug());*/		
	}

	/*
	 * @Test void testSaveCategoryModel() { fail("Not yet implemented"); }
	 * 
	 * @Test void testGetCategoryModelById() { fail("Not yet implemented"); }
	 * 
	 * @Test void testGetCategoryById() { fail("Not yet implemented"); }
	 * 
	 * @Test void testDeleteCategory() { fail("Not yet implemented"); }
	 * 
	 * @Test void testExistsCategoryById() { fail("Not yet implemented"); }
	 * 
	 * @Test void testExistsCategoryByName() { fail("Not yet implemented"); }
	 * 
	 * @Test void testExistsCategoryByNameExcludingId() {
	 * fail("Not yet implemented"); }
	 * 
	 * @Test void testCategoriasServiceImpl() { fail("Not yet implemented"); }
	 */
}
