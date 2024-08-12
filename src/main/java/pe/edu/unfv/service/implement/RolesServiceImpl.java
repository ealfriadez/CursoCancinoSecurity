package pe.edu.unfv.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.model.RolesModel;
import pe.edu.unfv.repository.IRolesRepository;
import pe.edu.unfv.service.IRolesService;

@Service
@Primary
@AllArgsConstructor
public class RolesServiceImpl implements IRolesService{
	
	private IRolesRepository repository;
	
	public List<RolesModel> getAllRol() {
		
		return this.repository.findAll();
	}

	@Override
	public Optional<RolesModel> getRolById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void saveAutorizar(RolesModel rol) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAutorization(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
}
