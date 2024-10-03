package pe.edu.unfv.service.implement;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import pe.edu.unfv.controller.dto.AuthCreateUserRequest;
import pe.edu.unfv.controller.dto.AuthLoginRequest;
import pe.edu.unfv.controller.dto.AuthResponse;
import pe.edu.unfv.persistence.dto.CategoryDTO;
import pe.edu.unfv.persistence.dto.ProductDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;
import pe.edu.unfv.persistence.entity.model.ProductosModel;
import pe.edu.unfv.persistence.entity.security.RoleEntity;
import pe.edu.unfv.persistence.entity.security.RoleEnum;
import pe.edu.unfv.persistence.entity.security.UserEntity;

public class DataProvider {

	public static List<CategoriasModel> categoriasModelListMock(){
		
		System.out.println(" -> Obteniendo listado CategoriasModel / Mock");
		
		return List.of(
					new CategoriasModel(1, "Lionel Messi", "Inter Miami"),
					new CategoriasModel(2, "Cristiano Ronaldo", "Al Nassr"),
					new CategoriasModel(3, "Neymar Jr.", "Paris Saint-Germain"),
					new CategoriasModel(4, "Kylian Mbappé", "Paris Saint-Germain"),
					new CategoriasModel(5, "Kevin De Bruyne", "Manchester City"),
					new CategoriasModel(6, "Virgil van Dijk", "Liverpool"));
	}
	
	/*
	public static Player playerMock(){
		
		System.out.println(" -> Obteniendo Player / Mock");
		
		return new Player(3L, "Neymar Jr.", "Paris Saint-Germain", "Delantero");
	}*/
	
	public static CategoryDTO categoryDTOMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		return new CategoryDTO("Virgil van Dijk", "Liverpool");
	}
	
	public static CategoryDTO categoryEmptyDTOMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		return new CategoryDTO();
	}
	
	public static CategoriasModel categoryModelNullMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		return null;
	}
	
	public static CategoryDTO categoryDTONullMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		return null;
	}
	
	public static CategoriasModel categoriasModelMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		return new CategoriasModel(6, "Virgil van Dijk", "Liverpool");
	}
	
	public static Optional<CategoriasModel> categoriasOptionalModelMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		CategoriasModel categoriasModel = new CategoriasModel(7, "Peñarol", "Marcador");
		
		Optional<CategoriasModel> optional = Optional.of(categoriasModel);
		
		return optional;
	}
	
	public static Optional<CategoriasModel> categoriasOptionalEmptyModelMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		Optional<CategoriasModel> optional = Optional.empty();
		
		return optional;
	}
	
	public static Optional<CategoryDTO> categoryDTOOptionalModelMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		CategoryDTO categoriasDTO = new CategoryDTO("Peñarol", "Marcador");
		
		Optional<CategoryDTO> optional = Optional.of(categoriasDTO);
		
		return optional;
	}
	
	public static Optional<CategoryDTO> categoryDTOOptionalEmptyModelMock(){
		
		//System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		Optional<CategoryDTO> optional = Optional.empty();
		
		return optional;
	}
	
	public static List<ProductosModel> productosModelListMock(){
			
			System.out.println(" -> Obteniendo listado ProductosModel / Mock");
			
			return List.of(
						new ProductosModel(1, "Lionel Messi", "Inter Miami", "Jugado 1", 8500, "mesi", "united"),
						new ProductosModel(2, "Cristiano Ronaldo", "Al Nassr", "Jugado 2", 2500, "ronaldo", "africa"),
						new ProductosModel(3, "Neymar Jr.", "Paris Saint-Germain", "Jugado 3", 3500, "neymar", "europa"),
						new ProductosModel(4, "Kylian Mbappé", "Paris Saint-Germain", "Jugado 4", 4500, "mbappe", "europa"),
						new ProductosModel(5, "Kevin De Bruyne", "Manchester City", "Jugado 5", 6500, "bruyne", "europa"),
						new ProductosModel(6, "Virgil van Dijk", "Liverpool", "Jugado 6", 7500, "van", "europa"));
	}

	public static ProductosModel productosModelMock(){		
		
		return new ProductosModel(1, "Lionel Messi", "Inter Miami", "Jugado 1", 8500, "mesi", "united");
	}
	
	
	public static ProductosModel productosModelMockCategory(){	
		
		CategoriasModel categoriaId = new CategoriasModel();
		categoriaId.setId(1);
		
		return new ProductosModel(1, "Lionel Messi", "Inter Miami", "Jugado 1", 8500, "mesi", "united", categoriaId);
	}
	
	public static ProductosModel productosModelMockCategory_(){	
		
		CategoriasModel categoriaId = new CategoriasModel();
		categoriaId.setId(1);
		
		byte[] arraytoinsert = new byte[]{0,1,2,3,4,5,6,7,8,9};
		
		return new ProductosModel(1, "Lionel Messi", "Inter Miami", "Jugado 1", 8500, "mesi.jpg", "united", arraytoinsert, categoriaId);
	}
	
	public static ProductosModel productosModelEmptyMock(){		
		
		return new ProductosModel();
	}
	
	public static ProductDTO productDTOMock(){		
		
		return new ProductDTO(1, "Lionel Messi", "Inter Miami", "Jugado 1", 8500, "mesi", 1);
	}
	
	public static ProductDTO productDTOEmptyMock(){		
		
		return null;
	}
	
	public static Optional<ProductDTO> productsOptionalDTOMock(){		
		
		ProductDTO productsOptional = new ProductDTO(1, "Lionel Messi", "Inter Miami", "Jugado 1", 8500, "mesi", 1);
		
		Optional<ProductDTO> optional = Optional.of(productsOptional);
		
		return optional;
	}
	
	public static Optional<ProductosModel> productsOptionalModelMock(){
		
		ProductosModel productosModel = new ProductosModel(1, "Lionel Messi", "Inter Miami", "Jugado 1", 8500, "mesi", "united");
		
		Optional<ProductosModel> optional = Optional.of(productosModel);
		
		return optional;
	}
	
	public static Optional<ProductosModel> productsOptionalModelNullMock(){		
		
		return null;
	}
	
	public static Optional<ProductosModel> productsOptionalModelEmptyModelMock(){
						
		Optional<ProductosModel> optional = Optional.empty();
		
		return optional;
	}
	
	public static AuthCreateUserRequest authCreateUserRequestEmpty(){		
		
		return new AuthCreateUserRequest(null, null, null, null);
	}
	
	public static AuthCreateUserRequest authCreateUserRequest(){		
		
		return new AuthCreateUserRequest("ealfriadez", "123456", "ealfriadez@gmail.com", null);
	}
	
	public static AuthResponse authResponseTrue(){		
		
		return new AuthResponse("ealfriadez", "123456", "ealfriadez@gmail.com", true);
	}
	
	public static AuthResponse authResponseFalse(){		
		
		return new AuthResponse("ealfriadez", "123456", "ealfriadez@gmail.com", false);
	}
	
	public static AuthLoginRequest authLoginRequestEmpty(){		
		
		return new AuthLoginRequest(null, null);
	}
	
	public static AuthLoginRequest authLoginRequest(){		
		
		return new AuthLoginRequest("ealfriadez", "123456");
	}
	
	public static String username = "ealfriadez";
	public static String usernameOther = "other";
	
	public static Optional<UserEntity> userEntityEmptyModelMock(){
		
		Optional<UserEntity> optional = Optional.empty();
		
		return optional;
	}
	
	public static Optional<UserEntity> userEntityModelMock(){		
	
		Set<RoleEntity> roles = new HashSet<>();		
		
		UserEntity userEntity = new UserEntity(1L, "Eleazar Alfredo", "ealfriadez@gmail.com", username, false, false, false, false, roles);
		
		Optional<UserEntity> optional = Optional.of(userEntity);
		
		return optional;
	}
}
