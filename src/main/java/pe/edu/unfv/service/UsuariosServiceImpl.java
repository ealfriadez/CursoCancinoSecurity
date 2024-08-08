package pe.edu.unfv.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import pe.edu.unfv.model.UsuariosModel;

@Service
@Primary
public class UsuariosServiceImpl implements IUsuariosService{

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
	public UsuariosModel saveCity(UsuariosModel usuario) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
