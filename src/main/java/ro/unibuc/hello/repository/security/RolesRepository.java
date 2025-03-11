package ro.unibuc.hello.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.unibuc.hello.entity.security.RoleEntity;

import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(String name);
}
