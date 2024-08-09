package pe.edu.unfv.service.implement;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pe.edu.unfv.model.UsuariosModel;
import pe.edu.unfv.repository.IUsuariosRepository;
import pe.edu.unfv.service.IUsuariosService;

@Service
@Primary
@AllArgsConstructor
public class UsuariosServiceImpl implements IUsuariosService{

	private IUsuariosRepository usuariosRepository;

	@Override
	public List<UsuariosModel> getAllNameUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<UsuariosModel> getUserById(Integer id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public UsuariosModel getUserByEmailAndEstate(String email, Integer estate) {		
	
		return this.usuariosRepository.findByCorreoAndEstado(email, estate);
	}

	@Override
	public UsuariosModel saveUser(UsuariosModel user) {
		
		return this.usuariosRepository.save(user);	
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub		
	}	
	
}
