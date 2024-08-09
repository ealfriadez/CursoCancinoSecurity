package pe.edu.unfv.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.model.AutorizarModel;
import pe.edu.unfv.repository.IAutorizarRepository;
import pe.edu.unfv.service.IAutorizarService;

@Service
@Primary
@AllArgsConstructor
public class AutorizarServiceImpl implements IAutorizarService{

	private IAutorizarRepository autorizarRepository;
	
	@Override
	public List<AutorizarModel> getAllNameRol() {

		List<AutorizarModel> list = this.autorizarRepository.findAll();
		return list;
	}

	@Override
	public Optional<AutorizarModel> getAutorizationByNameRol(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteAutorization(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveAutorizar(AutorizarModel autorizar) {
		
		this.autorizarRepository.save(autorizar);
	}
}
