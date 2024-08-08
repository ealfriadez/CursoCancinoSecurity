package pe.edu.unfv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.unfv.model.AutorizarModel;

@Repository
public interface IAutorizarRepository extends JpaRepository<AutorizarModel, Integer>{

}
