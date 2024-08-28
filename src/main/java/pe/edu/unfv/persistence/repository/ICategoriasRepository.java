package pe.edu.unfv.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.unfv.persistence.entity.model.CategoriasModel;

public interface ICategoriasRepository extends JpaRepository<CategoriasModel, Integer>{

	boolean existsCategoryByNombre(String name);
	Optional<CategoriasModel> findCategoriasModelByNombreAndIdNot(String name, int id);
}
