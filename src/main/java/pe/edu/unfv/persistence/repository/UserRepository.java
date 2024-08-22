package pe.edu.unfv.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pe.edu.unfv.persistence.entity.UserEntity;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long>{

	Optional<UserEntity> findByUsername(String username);
}
