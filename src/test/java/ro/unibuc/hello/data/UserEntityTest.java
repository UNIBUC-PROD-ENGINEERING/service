package ro.unibuc.hello.data;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserEntityTest {
    String id = "1";
    String email = "adresa@gmail.com";
    String parola = "parola123";

    @Test
    void testConstructor() {
        UserEntity UserEntity = new UserEntity(id, email,parola);

        assertEquals(id, UserEntity.getId());
        assertEquals(email, UserEntity.getEmail());
        assertEquals(parola, UserEntity.getParola());
    }

    @Test
    void testSetters() {
        UserEntity UserEntity = new UserEntity();

        assertEquals(null, UserEntity.getId());
        assertEquals(null, UserEntity.getEmail());
        assertEquals(null, UserEntity.getParola());

        UserEntity.setId(id);
        UserEntity.setEmail(email);
        UserEntity.setParola(parola);

        assertEquals(id, UserEntity.getId());
        assertEquals(email, UserEntity.getEmail());
        assertEquals(parola, UserEntity.getParola());
    }
}
