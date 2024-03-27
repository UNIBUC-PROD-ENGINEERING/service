package ro.unibuc.hello.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.data.UserRepository;
import ro.unibuc.hello.dto.UserDto;
import ro.unibuc.hello.exception.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Tag("IT")
class UserServiceTestIT {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test
    void testGetUser() throws Exception {
        UserEntity userEntity = new UserEntity("Nicusor", "Stanciu", 30, "stanciu10");
        userRepository.save(userEntity);

        UserEntity retrievedUser = userService.getUser(userEntity.id);

        assertNotNull(retrievedUser);
        assertEquals(userEntity.id, retrievedUser.id);
        assertEquals(userEntity.firstName, retrievedUser.firstName);
        assertEquals(userEntity.lastName, retrievedUser.lastName);
        assertEquals(userEntity.age, retrievedUser.age);
        assertEquals(userEntity.userName, retrievedUser.userName);
    }

    @Test
    void testGetUserNonExistent() {
        assertThrows(EntityNotFoundException.class, () -> userService.getUser("nonexistent_id"));
    }

    @Test
    void testAddUser() {
        UserDto newUser = new UserDto("Lucian", "Sanmartean", 35, "sanmartean18");
        Long initialCount = userRepository.count();

        String result = userService.addUser(newUser);

        assertEquals("User added", result);
        assertEquals(initialCount + 1, userRepository.count());
    }

    @Test
    void testDeleteUserById() {

        Long initialCount = userRepository.count();
        UserEntity userEntity = new UserEntity("Liviu", "Antal", 28, "liviuantal7");
        userRepository.save(userEntity);

        String result = userService.deleteUserById(userEntity.id);

        assertEquals("User deleted", result);
        assertEquals(initialCount.longValue(), userRepository.count());
    }

    @Test
    void testDeleteUserByIdNonExistent() {

        assertThrows(EntityNotFoundException.class, () -> userService.deleteUserById("nonexistent_id"));
    }
}
