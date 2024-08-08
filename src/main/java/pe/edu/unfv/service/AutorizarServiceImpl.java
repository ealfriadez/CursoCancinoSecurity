package pe.edu.unfv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import pe.edu.unfv.model.AutorizarModel;
import pe.edu.unfv.repository.IAutorizarRepository;

@Service
@Primary
public class AutorizarServiceImpl implements IAutorizarService{

	@Autowired
	private IAutorizarRepository autorizarRepository;
	
	@Override
	public List<AutorizarModel> getAllNameRol() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AutorizarModel> getAutorizationByNameRol(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public AutorizarModel saveCity(AutorizarModel city) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAutorization(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
