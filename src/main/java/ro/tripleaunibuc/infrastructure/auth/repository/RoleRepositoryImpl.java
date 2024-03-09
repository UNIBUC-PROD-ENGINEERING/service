package ro.tripleaunibuc.infrastructure.auth.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ro.tripleaunibuc.domain.auth.model.Role;
import ro.tripleaunibuc.domain.auth.repository.RoleRepository;

import java.util.List;

@Component
@Primary
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final SpringDataRoleRepository springDataRoleRepository;

    @Override
    public List<Role> findByUsername(String username) {
        return springDataRoleRepository.findByUsername(username);
    }
}
