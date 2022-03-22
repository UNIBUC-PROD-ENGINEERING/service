package ro.unibuc.hello.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {

    }

    @Test
    void registerUser() {
        User user = new User(
            "Florian",
            "Marcu"
        );
        String userId = user.userId;
        UserController controller = new UserController();
        controller.registerUser(user);
        when(mockUserRepository.findUserById(userId)).thenReturn(user);
        Assertions.assertEquals(user.getUserId(),  userId);

    }

    @Test
    void getAllUsers() {
        UserController controller = new UserController();
        List<User> users = controller.getAllUsers();
        when(mockUserRepository.findAll()).thenReturn(users);
        // TODO: Assert
    }
}