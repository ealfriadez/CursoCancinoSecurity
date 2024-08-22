package pe.edu.unfv;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pe.edu.unfv.persistence.entity.PermissionEntity;
import pe.edu.unfv.persistence.entity.RoleEntity;
import pe.edu.unfv.persistence.entity.RoleEnum;
import pe.edu.unfv.persistence.entity.UserEntity;
import pe.edu.unfv.persistence.repository.UserRepository;

@SpringBootApplication
public class CursoCancinoSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(CursoCancinoSecurityApplication.class, args);
	}	
	/*
	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		
		return args ->{
			
			//Create PERMISSIONS
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();
			
			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();
			
			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();
			
			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();
			
			PermissionEntity refactorPermission = PermissionEntity.builder()
					.name("REFACTOR")
					.build();
			
			//Create ROLES
			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();
			
			RoleEntity rolesUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(createPermission, readPermission))
					.build();
			
			RoleEntity roleInvited = RoleEntity.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissionList(Set.of(readPermission))
					.build();
			
			RoleEntity roleDeveloper = RoleEntity.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission, refactorPermission))
					.build();
			
			//Create USERS
			UserEntity userSantiago = UserEntity.builder()
					.username("santiago")
					.correo("snalfriadez@gmail.com")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleAdmin))
					.build();
			
			UserEntity userSebastian = UserEntity.builder()
					.username("sebastian")
					.correo("salfriadez@gmail.com")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(rolesUser))
					.build();
			
			UserEntity userPilar = UserEntity.builder()
					.username("pilar")
					.correo("pilaxis@gmail.com")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleInvited))
					.build();
			
			UserEntity userMoka = UserEntity.builder()
					.username("moka")
					.correo("moka@gmail.com")
					.password("1234")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleDeveloper))
					.build();
			
			userRepository.saveAll(List.of(userSantiago, userSebastian, userPilar, userMoka));
		};		
	}
	*/
}
