package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.*;
import ro.unibuc.hello.data.SubscriptionEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.SubscriptionRepository;
import ro.unibuc.hello.data.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class AccountServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private GamesService gamesService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UserEntity createTestUser(String username, String password, int tier, Date expirationDate) {
        UserEntity user = new UserEntity(username, password);
        user.setId("1");
        user.setTier(tier);
        user.setExpirationDate(expirationDate);
        return user;
    }

    @Test
    void testGetUpgrades_InvalidUsername() {
        // Arrange
        when(userRepository.findByUsernameContaining("unknown")).thenReturn(Collections.emptyList());

        // Act
        String result = accountService.getUpgrades("unknown", "password");

        // Assert
        assertEquals("Invalid username!", result);
    }

    @Test
    void testGetUpgrades_InvalidPassword() {
        // Arrange
        UserEntity user = createTestUser("testuser", "correctpass", 1, new Date());
        when(userRepository.findByUsernameContaining("testuser")).thenReturn(Collections.singletonList(user));

        // Act
        String result = accountService.getUpgrades("testuser", "wrongpass");

        // Assert
        assertEquals("Invalid password!", result);
    }

    
}

