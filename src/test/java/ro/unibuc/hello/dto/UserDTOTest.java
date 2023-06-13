package ro.unibuc.hello.dto;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class UserDTOTest {
    String id = "1";
    String email = "adresa@gmail.com";
    String parola = "parola123";

    @Test
    void testConstructor() {
        UserDTO userDTO = new UserDTO(id, email,parola);

        assertEquals(id, userDTO.getId());
        assertEquals(email, userDTO.getEmail());
        assertEquals(parola, userDTO.getParola());
    }

    @Test
    void testSetters() {
        UserDTO userDTO = new UserDTO();

        assertEquals(null, userDTO.getId());
        assertEquals(null, userDTO.getEmail());
        assertEquals(null, userDTO.getParola());

        userDTO.setId(id);
        userDTO.setEmail(email);
        userDTO.setParola(parola);

        assertEquals(id, userDTO.getId());
        assertEquals(email, userDTO.getEmail());
        assertEquals(parola, userDTO.getParola());
    }
}
