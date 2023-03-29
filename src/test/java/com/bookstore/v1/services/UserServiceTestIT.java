package com.bookstore.v1.services;

import com.bookstore.v1.data.*;
import com.bookstore.v1.dto.UserDTO;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("IT")
@DisplayName("User Service Integration Test")
public class UserServiceTestIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private User user1;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        user1 = new User("Raluki123", "raluca.ioana@example.com", "0745678922");
        userRepository.save(user1);
    }

    @Test
    public void testAddUser() {
        UserDTO dto = new UserDTO(null, "Test userName", "Test email", "Test phoneNumber");

        UserDTO result = userService.addUser(dto);

        assertNotNull(result);
        assertEquals(result.getUserName(),dto.getUserName());

        User savedUser = userRepository.findById(result.getId()).orElse(null);

        assertNotNull(savedUser);
        assertEquals(savedUser.getUserName(),dto.getUserName());
    }

    @Test
    public void testUpdateUser() {
        User user = new User("RalucaSandu", "raluca@email.com", "0745679011");
        user.setUserName("RalucaSandu");
        user = userRepository.save(user);

        UserDTO dto = new UserDTO(user.getId(), "Updated userName", user.getEmail(), user.getPhoneNumber());

        UserDTO result = userService.updateUser(dto);


        assertNotNull(result);
        assertEquals(dto.getUserName(), result.getUserName());

        User updatedUser = userRepository.findById(result.getId()).orElse(null);
        assertNotNull(updatedUser);
        assertEquals(dto.getUserName(), updatedUser.getUserName());
    }

    @Test
    public void getUsersTest() {
        List<UserDTO> users = userService.getUsers();
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("Raluki123", users.get(0).getUserName());
    }

    @Test
    public void testDeleteUserById() {
        User user = new User("Test userName", "Test email", "Test phoneNumber");
        user.setUserName("Raluki123");
        user = userRepository.save(user);

        userService.deleteUserById(user.getId());

        User deletedUser = userRepository.findById(user.getId()).orElse(null);
        assertNull(deletedUser);
    }
}