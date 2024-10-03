package pe.edu.unfv.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;

import pe.edu.unfv.controller.dto.AuthCreateUserRequest;
import pe.edu.unfv.persistence.entity.security.UserEntity;
import pe.edu.unfv.persistence.repository.security.RoleRepository;
import pe.edu.unfv.persistence.repository.security.UserRepository;
import pe.edu.unfv.service.implement.DataProvider;
import pe.edu.unfv.util.security.JwtUtils;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest {

	@Mock
	UserRepository userRepository;

	@Mock
	JwtUtils jwtUtils;

	@Mock
	PasswordEncoder passwordEncoder;

	@Mock
	RoleRepository roleRepository;

	@InjectMocks
	UserDetailServiceImpl userDetailServiceImpl;

	@Test
	void testLoadUserByUsername() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		String username = DataProvider.username;
		Optional<UserEntity> userEntity = DataProvider.userEntityModelMock();

		// When -> Cuando
		// Simular el comportamiento del repositorio		
		Mockito.when(this.userRepository.findUserEntityByUsername(username)).thenReturn(userEntity);

		UserDetails response = this.userDetailServiceImpl.loadUserByUsername(username);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals("Eleazar Alfredo", response.getUsername());
	}
/*
	@Test
	void testAuthenticate() {
		fail("Not yet implemented");
	}

	@Test
	void testLoginUser() {
		fail("Not yet implemented");
	}

	@Test
	void testCreateUser() {
		fail("Not yet implemented");
	}
*/
}
