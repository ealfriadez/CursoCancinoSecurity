package pe.edu.unfv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.unfv.model.RolesModel;

public interface IRolesRepository extends JpaRepository<RolesModel, Integer>{

}
