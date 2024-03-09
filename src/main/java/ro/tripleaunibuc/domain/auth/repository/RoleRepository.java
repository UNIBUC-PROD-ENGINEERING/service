package ro.tripleaunibuc.domain.auth.repository;

import ro.tripleaunibuc.domain.auth.model.Role;

import java.util.List;

public interface RoleRepository {

    List<Role> findByUsername(String username);
}
