package pe.edu.unfv.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.unfv.persistence.entity.model.ProductosModel;


public interface IProductosRepository extends JpaRepository<ProductosModel, Integer>{	

	boolean existsProductByNombre(String nombre);
	Optional<ProductosModel> findProductosModelByNombreFoto(String nombreFoto);
}