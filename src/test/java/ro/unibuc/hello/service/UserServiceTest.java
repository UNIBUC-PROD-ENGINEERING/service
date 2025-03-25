package ro.unibuc.hello.service;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.Role;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.data.UserSearchRepository;
import ro.unibuc.hello.dto.request.RegisterDto;
import ro.unibuc.hello.dto.response.UserDto;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private UserSearchRepository userSearchRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserDetails userDetails;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserService userService;

    private UserEntity user;

    private UserDto userDto;

    @BeforeEach
    void setUp(){
        user = UserEntity.builder()
            .username("user")
            .email("email")
            .password("password")
            .role("USER")
            .build();
        userDto = UserDto.builder()
            .username("user")
            .email("email")
            .role("USER")
            .build();
        SecurityContextHolder.setContext(securityContext);

    }

    @Test
    void loadByUsername_ShouldReturnUser(){
        
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        when(userRepository.findByUsername("user")).thenReturn(Optional.ofNullable(user));

        var userDetails = userService.loadUserByUsername("user");

        assertEquals("user",userDetails.getUsername());
        assertEquals("password",userDetails.getPassword());
        assertEquals(Set.of(authority),userDetails.getAuthorities());
    }

    @Test
    void getUser_ShouldThrowException_WhenUserDoesNotExist(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,()->userService.getUser(user.getUsername()));
    }
    
    @Test
    void getUser_ShouldReturnUser_WhenUserExists(){
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        var dto = userService.getUser(user.getUsername());

        assertEquals("user",dto.getUsername());
        assertEquals("email",dto.getEmail());
        assertEquals("USER", dto.getRole());
    }

    @Test
    void getSelf_ShouldThrowException_WhenUserDoesNotExist(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getAuthenticatedUser());
    }

    @Test
    void getSelf_shouldReturnAuthenticatedUser_WhenUserExists(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        var user = userService.getSelf();

        assertEquals("user",user.getUsername());
        assertEquals("email",user.getEmail());
        assertEquals("USER", user.getRole());
    }

    @Test
    void getAll_shouldReturnAllUsers(){
        UserEntity otherUser = UserEntity.builder()
                            .username("other")
                            .email("otheremail")
                            .password("otherpassword")
                            .build();
        UserDto otherUserDto = UserDto.builder()
                            .username("other")
                            .email("otheremail")
                            .build();
        when(userRepository.findAll()).thenReturn(List.of(user,otherUser));
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);
        when(modelMapper.map(otherUser,UserDto.class)).thenReturn(otherUserDto);

        var userList = userService.getAll();

        assertNotNull(userList);
        assertEquals(2, userList.getUserList().size());

        verify(userRepository, times(1)).findAll();
        
        assertEquals("user",userList.getUserList().get(0).getUsername());
        assertEquals("other",userList.getUserList().get(1).getUsername());
    }

    @Test
    void getRelevantUsers_ShouldReturnAllUsers_WithTheKeywordInTheirUsername(){
        when(userSearchRepository.searchUsers("user")).thenReturn(List.of(user));
        when(modelMapper.map(user,UserDto.class)).thenReturn(userDto);

        var userList = userService.getRelevantUsers("user");

        assertNotNull(userList);
        assertEquals(1, userList.getUserList().size());;
        assertEquals("user",userList.getUserList().get(0).getUsername());
    }

    @Test
    void updateUser_ShouldNotUpdateUsername_WhenUserDoesNotExist(){
        RegisterDto registerDto = RegisterDto.builder()
                                .username("user")
                                .build();
        assertThrows(EntityNotFoundException.class,()->userService.updateUser("someOtherUsername", registerDto));
    }

    @Test
    void updateUser_ShouldUpdateUser_WhenUserExists(){
        RegisterDto registerDto = RegisterDto.builder()
                                .username("user")
                                .password("updatedpassword")
                                .email("updatedemail")
                                .build();
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUser("user", registerDto);

        verify(userRepository,times(1)).save(any());
    }

    @Test
    void delete_UserShouldNotDeleteThemselves(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class,()->userService.delete("user"));
    }

    @Test
    void delete_ShouldNotDeleteAdmin(){
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("user");
        user.setUsername("otherUsername");
        user.setRole(Role.ADMIN);
        when(userRepository.findByUsername("otherUsername")).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class,()->userService.delete("otherUsername"));
    }


    @Test
    void delete_ShouldDeleteAnotherUser(){
        UserEntity loggedInUser = UserEntity.builder()
                                .username("otherUser")
                                .build();
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        when(userDetails.getUsername()).thenReturn("otherUser");
        when(userRepository.findByUsername(anyString()))
        .thenReturn(Optional.of(user))
        .thenReturn(Optional.of(loggedInUser));

        userService.delete("user");

        verify(userRepository,times(1)).delete(user);
    }
}
