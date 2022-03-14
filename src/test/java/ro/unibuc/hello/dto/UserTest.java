package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    User user = new User("Alexandru", "Voiculescu");

    @Test
    void getUserId() {
    }

    @Test
    void getFirstName() {
        Assertions.assertSame("Alexandru", user.getFirstName());
    }

    @Test
    void getLastName() {
        Assertions.assertSame("Voiculescu", user.getLastName());
    }

    @Test
    void setFirstName() {
        user.setFirstName("Andrei");
        Assertions.assertSame("Andrei", user.getFirstName());
    }

    @Test
    void setLastName() {
        user.setLastName("Peles");
        Assertions.assertSame("Peles", user.getLastName());
    }
}