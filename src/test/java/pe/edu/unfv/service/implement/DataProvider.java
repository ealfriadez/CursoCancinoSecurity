package pe.edu.unfv.service.implement;

import java.util.List;

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
		
		System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		return new CategoryDTO("Fiorentina", "Marcador");
	}
	
	public static CategoriasModel newCategoriasModelMock(){
		
		System.out.println(" -> Obteniendo CategoryDTO / Mock");
		
		return new CategoriasModel(7, "Fiorentina", "Marcador");
	}
}
