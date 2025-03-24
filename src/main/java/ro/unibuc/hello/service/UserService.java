package ro.unibuc.hello.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ro.unibuc.hello.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.UserSearchRepository;
import ro.unibuc.hello.data.Role;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;
import ro.unibuc.hello.dto.response.UserListDto;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSearchRepository userSearchRepository;
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(()->new EntityNotFoundException(username));

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(authority)
        );
    }

    // Helper method: get any user from database using their username
    private UserEntity loadUser(String username) {
        return userRepository.findByUsername(username)
                                .orElseThrow(()->new EntityNotFoundException("user"));
    }

    //Helper method: get the logged in user

    public UserEntity getAuthenticatedUser(){
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username)
                                .orElseThrow(()->new EntityNotFoundException("user"));
    }

    public UserDto getSelf(){
        var user = getAuthenticatedUser();
        return modelMapper.map(user,UserDto.class);
    }
    

    public UserDto getUser(String username) {
        var user = loadUser(username);
        return modelMapper.map(user, UserDto.class);
    }


    public UserDto updateUser(String username, RegisterDto registerDto){
        if (registerDto.getUsername()!=username){
            throw new EntityNotFoundException("user");
        }
        var user = loadUser(username);
        
        user.setEmail(registerDto.getEmail());
        user.setPassword(registerDto.getPassword());

        // Save the updated user
        userRepository.save(user);

        return modelMapper.map(user, UserDto.class);
    }


    public UserListDto getAll() {
        System.out.println("Getting all users...");
        return new UserListDto(userRepository.findAll().stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .toList());
    }

    public UserListDto getRelevantUsers(String keyword){
        return new UserListDto(userSearchRepository.searchUsers(keyword).stream()
                              .map(user -> modelMapper.map(user,UserDto.class))
                              .toList());
    }

    public void delete(String username){
        var user = loadUser(username);
        if (username!=getAuthenticatedUser().getUsername() && user.getRole()!=Role.ADMIN)
            userRepository.delete(user);
        else {
            throw new EntityNotFoundException("user");
        }
    }
}
