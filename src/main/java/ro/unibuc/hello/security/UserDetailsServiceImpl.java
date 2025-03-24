package ro.unibuc.hello.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ro.unibuc.hello.data.User;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.exception.UserNotFoundException;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private  UserRepository userRepository;

    public UserDetailsServiceImpl() {
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user =  userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.getIsEnabled())
                .roles("USER")
                .build();
    }
}
