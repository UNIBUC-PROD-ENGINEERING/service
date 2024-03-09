package ro.tripleaunibuc.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.tripleaunibuc.domain.auth.model.Role;
import ro.tripleaunibuc.domain.auth.model.User;
import ro.tripleaunibuc.infrastructure.auth.repository.SpringDataRoleRepository;
import ro.tripleaunibuc.infrastructure.auth.repository.SpringDataUserRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final SpringDataUserRepository userRepository;
    private final SpringDataRoleRepository springDataRoleRepository;

    @Cacheable("userDetails")
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException(username));

        List<Role> roles = springDataRoleRepository.findByUsername(username);

        Collection<? extends GrantedAuthority> authorities = mapRolesToAuthorities(roles);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    private static Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                    .map(role -> new SimpleGrantedAuthority(role.getRole()))
                    .collect(Collectors.toList());
    }
}
