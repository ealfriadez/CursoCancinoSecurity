package pe.edu.unfv.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.model.CategoriasModel;
import pe.edu.unfv.repository.ICategoriasRepository;
import pe.edu.unfv.service.ICategoriasService;

@Service
@Primary
@AllArgsConstructor
public class CategoriasServiceImpl implements ICategoriasService{

	private ICategoriasRepository iCategoriasRepository;
	
	@Override
	public List<CategoriasModel> getAllCategorys() {
		
		return this.iCategoriasRepository.findAll(Sort.by("id").descending());
	}

	@Override
	public CategoriasModel getCategoryById(Integer id) {
		
		Optional<CategoriasModel> optional = this.iCategoriasRepository.findById(id);
		if (optional.isPresent()) {
			return optional.get();
		}		
		
		return null;
	}

	@Override
	public void saveCategory(CategoriasModel category) {
		
		this.iCategoriasRepository.save(category);
	}

	@Override
	public void deleteCategory(Integer id) {
		
		this.iCategoriasRepository.deleteById(id);
	}

}
