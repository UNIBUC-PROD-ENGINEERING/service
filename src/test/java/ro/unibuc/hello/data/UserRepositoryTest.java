package ro.unibuc.hello.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ro.unibuc.hello.dto.User;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void dataSetup(){
        User user = new User("Alexandru", "Voiculescu");

        user.setUserId("1kl3j1j2lk3");
        userRepository.save(user);
    }

    @Test
    void findUserById() {
        User user = new User("Alexandru", "Voiculescu");

        user.setUserId("1kl3j1j2lk3");

        User result = userRepository.findUserById("1kl3j1j2lk3");

        assertEquals(result.getFirstName(), user.getFirstName());
        assertEquals(result.getLastName(), user.getLastName());
    }
}