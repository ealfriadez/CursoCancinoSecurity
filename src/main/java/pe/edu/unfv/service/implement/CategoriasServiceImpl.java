package pe.edu.unfv.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.persistence.dto.CategoryDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;
import pe.edu.unfv.persistence.repository.ICategoriasRepository;
import pe.edu.unfv.service.ICategoriasService;
import pe.edu.unfv.util.Utilidades;

@Service
@Primary
@AllArgsConstructor
public class CategoriasServiceImpl implements ICategoriasService{

	private ICategoriasRepository iCategoriasRepository;
	
	@Override
	public List<CategoriasModel> getAllCategorys() {
		
		return this.iCategoriasRepository.findAll();
		//return this.iCategoriasRepository.findAll(Sort.by("id").descending());
	}

	@Override
	public CategoryDTO saveCategory(CategoryDTO categoryDTO) {
		
		CategoriasModel categoriasModel = convertCategoryToEntity(categoryDTO);	
				
		return convertCategoryToDTO(this.iCategoriasRepository.save(categoriasModel));
	}	
	
	@Override
	public void saveCategoryModel(CategoriasModel categoriasModel) {

		this.iCategoriasRepository.save(categoriasModel);		
	}
	
	@Override
	public CategoriasModel getCategoryModelById(int id) {
		
		Optional<CategoriasModel> categoriasModel = this.iCategoriasRepository.findById(id);
		
		if (categoriasModel.isPresent()) {
			return categoriasModel.get();
		}
		
		return null;
	}
	
	@Override
	public CategoryDTO getCategoryById(Integer id) {
		
		return this.iCategoriasRepository.findById(id)
				.map(this::convertCategoryToDTO)
				.orElse(null);
	}	

	@Override
	public void deleteCategory(int id) {
		
		this.iCategoriasRepository.deleteById(id);
	}

	@Override
	public boolean existsCategoryById(Integer id) {
		
		return this.iCategoriasRepository.existsById(id);
	}

	@Override
	public boolean existsCategoryByName(String name) {
		
		return this.iCategoriasRepository.existsCategoryByNombre(name);
	}
	
	@Override
	public boolean existsCategoryByNameExcludingId(String name, int id) {
		
		return this.iCategoriasRepository.findCategoriasModelByNombreAndIdNot(name, id).isPresent();
	}	
	
	public CategoryDTO convertCategoryToDTO(CategoriasModel categoriasModel) {
		
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setNombre(categoriasModel.getNombre());	
		categoryDTO.setSlug(categoriasModel.getSlug());	
		
		return categoryDTO;
	}
	
	public CategoriasModel convertCategoryToEntity(CategoryDTO categoryDTO) {
		
		CategoriasModel category = new CategoriasModel();
        category.setNombre(categoryDTO.getNombre());
        category.setSlug(Utilidades.getSlug(categoryDTO.getNombre()));        
        
        return category;
    }		
}
