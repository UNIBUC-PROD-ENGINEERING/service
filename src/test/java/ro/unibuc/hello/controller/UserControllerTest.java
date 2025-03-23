package ro.unibuc.hello.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import ro.unibuc.hello.controller.UserController;
import ro.unibuc.hello.data.PartyEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.repositories.PartyRepository;
import ro.unibuc.hello.repositories.UserRepository;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PartyRepository partyRepository;

    @InjectMocks
    private UserController userController;

    private UserEntity user;
    private PartyEntity party;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setId("user123");
        user.setName("John Doe");

        party = new PartyEntity();
        party.setId("party123");
        party.setName("Birthday Party");
        party.setUserIds(new ArrayList<>());
    }

    @Test
    public void testGetAllUsers_Success() {
        List<UserEntity> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        List<UserEntity> result = userController.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getName());
    }

    @Test
    public void testGetUserWithParties_UserNotFound() {
        when(userRepository.findById("invalidUser")).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.getUserWithParties("invalidUser");
        assertEquals(404, response.getStatusCode().value());
        assertEquals("User not found", response.getBody());
    }

    @Test
    public void testAddUserToParty_Success() {
        when(userRepository.findById("user123")).thenReturn(Optional.of(user));
        when(partyRepository.findById("party123")).thenReturn(Optional.of(party));
        when(partyRepository.save(any(PartyEntity.class))).thenReturn(party);

        ResponseEntity<?> response = userController.addUserToParty("user123", "party123");
        assertEquals(200, response.getStatusCode().value());
        assertTrue(party.getUserIds().contains("user123"));
    }

    @Test
    public void testAddUserToParty_UserOrPartyNotFound() {
        when(userRepository.findById("user123")).thenReturn(Optional.empty());
        when(partyRepository.findById("party123")).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.addUserToParty("user123", "party123");
        assertEquals(404, response.getStatusCode().value());
        assertEquals("User or Party not found", response.getBody());
    }

    @Test
    public void testCreateUser_Success() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        UserEntity result = userController.createUser(user);
        assertEquals("John Doe", result.getName());
    }

    @Test
    public void testUpdateUser_Success() {
        when(userRepository.existsById("user123")).thenReturn(true);
        when(userRepository.save(any(UserEntity.class))).thenReturn(user);

        ResponseEntity<UserEntity> response = userController.updateUser("user123", user);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(user, response.getBody());
    }

    @Test
    public void testUpdateUser_UserNotFound() {
        when(userRepository.existsById("user123")).thenReturn(false);

        ResponseEntity<UserEntity> response = userController.updateUser("user123", user);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void testDeleteUser_Success() {
        when(userRepository.existsById("user123")).thenReturn(true);
        doNothing().when(userRepository).deleteById("user123");

        ResponseEntity<Void> response = userController.deleteUser("user123");
        assertEquals(204, response.getStatusCode().value());
    }

    @Test
    public void testDeleteUser_UserNotFound() {
        when(userRepository.existsById("user123")).thenReturn(false);

        ResponseEntity<Void> response = userController.deleteUser("user123");
        assertEquals(404, response.getStatusCode().value());
    }
}
