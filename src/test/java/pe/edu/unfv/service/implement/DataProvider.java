package pe.edu.unfv.service.implement;

import java.util.List;
import java.util.Optional;

import pe.edu.unfv.persistence.dto.CategoryDTO;
import pe.edu.unfv.persistence.entity.model.CategoriasModel;

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
}
