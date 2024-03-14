package ro.unibuc.hello.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.hello.data.entity.Actor;
import ro.unibuc.hello.data.entity.Movie;
import ro.unibuc.hello.data.entity.Role;
import ro.unibuc.hello.data.repository.RoleRepository;

@Service
public class RoleService {
    @Autowired private RoleRepository roleRepository;

    public Role addRole(Actor actor, Movie movie) {
        return roleRepository.save(Role.builder()
                .actor(actor)
                .movie(movie)
                .build());
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow();
    }
}
