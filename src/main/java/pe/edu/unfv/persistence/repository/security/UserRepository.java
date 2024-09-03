package pe.edu.unfv.persistence.repository.security;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pe.edu.unfv.persistence.entity.security.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{

	Optional<UserEntity> findUserEntityByUsername(String username);
	Optional<UserEntity> findUserEntityByEmail(String email);
}
