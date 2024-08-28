package pe.edu.unfv.persistence.repository.security;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pe.edu.unfv.persistence.entity.security.RoleEntity;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long>{

	//Query de roles que siempre existan
	List<RoleEntity> findRoleEntitiesByRoleEnumIn(List<String> roleNames);
}
