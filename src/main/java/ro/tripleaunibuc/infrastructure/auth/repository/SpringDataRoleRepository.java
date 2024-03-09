package ro.tripleaunibuc.infrastructure.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tripleaunibuc.domain.auth.model.Role;

import java.util.List;

public interface SpringDataRoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByUsername(String username);
}
