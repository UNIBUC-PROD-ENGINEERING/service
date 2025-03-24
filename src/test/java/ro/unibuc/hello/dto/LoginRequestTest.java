package ro.unibuc.hello.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LoginRequestTest {

    LoginRequest loginRequest = new LoginRequest("username", "password");
    LoginRequest emptyLoginRequest = new LoginRequest();

    @Test
    void testGetUsername() {
        assertEquals("username", loginRequest.getUsername());   
    }
    
    @Test
    void testGetPassword() {
        assertEquals("password", loginRequest.getPassword());
    }

    @Test
    void testSetUsername() {
        emptyLoginRequest.setUsername("username 2");
        assertEquals("username 2", emptyLoginRequest.getUsername());
    }
    
    @Test
    void testSetPassword() {
        emptyLoginRequest.setPassword("password 2");
        assertEquals("password 2", emptyLoginRequest.getPassword());
    }
}
