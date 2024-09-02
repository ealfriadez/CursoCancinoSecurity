package pe.edu.unfv.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.unfv.persistence.entity.model.ImageDataModel;

public interface IImageDataRepository extends JpaRepository<ImageDataModel, Integer>{

	Optional<ImageDataModel> findImageDataModelByName(String fileName);
}
