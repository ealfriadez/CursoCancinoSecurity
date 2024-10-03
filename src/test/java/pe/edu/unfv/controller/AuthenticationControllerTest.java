package pe.edu.unfv.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import pe.edu.unfv.controller.dto.AuthCreateUserRequest;
import pe.edu.unfv.controller.dto.AuthLoginRequest;
import pe.edu.unfv.controller.dto.AuthResponse;
import pe.edu.unfv.security.service.UserDetailServiceImpl;
import pe.edu.unfv.service.implement.DataProvider;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

	@Mock
	UserDetailServiceImpl userDetailServiceImpl;

	@InjectMocks
	AuthenticationController authenticationController;

	@Test
	void testRegisterErrorsTrue() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		AuthCreateUserRequest authCreateUserRequest = DataProvider.authCreateUserRequestEmpty();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);

		ResponseEntity<?> response = this.authenticationController.register(authCreateUserRequest, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testRegisterErrorsFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		AuthCreateUserRequest authCreateUserRequest = DataProvider.authCreateUserRequestEmpty();
		AuthResponse authResponse = DataProvider.authResponseTrue();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(this.userDetailServiceImpl.createUser(authCreateUserRequest)).thenReturn(authResponse);

		ResponseEntity<?> response = this.authenticationController.register(authCreateUserRequest, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		// Verificar las interacciones del repositorio (Mockito-specific)
		// Asegurarse de que el repositorio se haya llamado con el modelo esperado
		verify(this.userDetailServiceImpl).createUser(authCreateUserRequest);
	}

	@Test
	void testLoginErrorsTrue() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		AuthLoginRequest authLoginRequest = DataProvider.authLoginRequestEmpty();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(true);

		ResponseEntity<?> response = this.authenticationController.login(authLoginRequest, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void testLoginErrorsFalse() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		AuthLoginRequest authLoginRequest = DataProvider.authLoginRequest();
		AuthResponse authResponse = DataProvider.authResponseTrue();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(this.userDetailServiceImpl.loginUser(authLoginRequest)).thenReturn(authResponse);// Este se niega

		ResponseEntity<?> response = this.authenticationController.login(authLoginRequest, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testLoginErrorsFalseLoginUser() {

		// Given -> Mientras
		// Convertir para el comportamiento esperado
		AuthLoginRequest authLoginRequest = DataProvider.authLoginRequest();
		AuthResponse authResponse = DataProvider.authResponseFalse();

		// When -> Cuando
		// Simular el comportamiento del repositorio
		BindingResult mockBindingResult = Mockito.mock(BindingResult.class);
		Mockito.when(mockBindingResult.hasErrors()).thenReturn(false);
		Mockito.when(this.userDetailServiceImpl.loginUser(authLoginRequest)).thenReturn(authResponse);// Este se niega

		ResponseEntity<?> response = this.authenticationController.login(authLoginRequest, mockBindingResult);

		// Then -> entonces
		// Verificar los resultados correctamentes
		assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
	}

}
