package ro.unibuc.hello.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ro.unibuc.hello.data.*;
import ro.unibuc.hello.dto.*;
import ro.unibuc.hello.data.SubscriptionEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.data.SubscriptionRepository;
import ro.unibuc.hello.data.UserRepository;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    private GameRepository gameRepository;

    @Mock
    private GamesService gamesService;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // *** MIHAI ***

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

    @Test
    void testGetUpgrades_WithActiveSubscription() {
        // Arrange - User has tier 2 (115$)
        Date futureDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        UserEntity user = createTestUser("testuser", "password", 2, futureDate);
        
        List<SubscriptionEntity> subscriptions = Arrays.asList(
            new SubscriptionEntity(1, 90),   // Tier 1
            new SubscriptionEntity(2, 115),  // Current tier (tier 2)
            new SubscriptionEntity(3, 150)   // Tier 3
        );
        
        when(userRepository.findByUsernameContaining("testuser")).thenReturn(Collections.singletonList(user));
        when(subscriptionRepository.findAll()).thenReturn(subscriptions);

        // Act
        String result = accountService.getUpgrades("testuser", "password");
        System.out.println("DEBUG - Actual output:\n" + result);

        // Assert
        // Current tier shows original price
        assertTrue(result.contains("Tier 2 for 115$ (Original Price)"));
        
        // Lower tier (tier 1) shows original price (no discount for downgrades)
        assertTrue(result.contains("Tier 1 for 90$ (Original Price)"));
        
        // Tier 3 upgrade calculation:
        // 150 (tier3) - 115 (current) = 35 difference
        // 35 * 0.8 = 28 discount price
        assertTrue(result.contains("Tier 3 for 28$ (Original: 35$)"));
    }

    // @Test
    // void testGetUpgrades_WithLatestTier() {
    //     // Arrange
    //     Date futureDate = Date.from(LocalDate.now().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    //     UserEntity user = createTestUser("testuser", "password", 3, futureDate);
        
    //     List<SubscriptionEntity> subscriptions = Arrays.asList(
    //         new SubscriptionEntity(1, 90),
    //         new SubscriptionEntity(2, 115),
    //         new SubscriptionEntity(3, 150)
    //     );
        
    //     when(userRepository.findByUsernameContaining("testuser")).thenReturn(Collections.singletonList(user));
    //     when(subscriptionRepository.findAll()).thenReturn(subscriptions);
    
    //     // Act
    //     String result = accountService.getUpgrades("testuser", "password");
    
    //     // Assert
    //     assertEquals("You already have access to all the games!", result);
    // }

    @Test
    void testCancelSubscription_InvalidUsername() {
        // Arrange
        when(userRepository.findByUsernameContaining("unknown")).thenReturn(Collections.emptyList());

        // Act
        String result = accountService.cancelSubscription("unknown", "password");

        // Assert
        assertEquals("Invalid username!", result);
    }

    @Test
    void testCancelSubscription_InvalidPassword() {
        // Arrange
        UserEntity user = createTestUser("testuser", "correctpass", 1, new Date());
        when(userRepository.findByUsernameContaining("testuser")).thenReturn(Collections.singletonList(user));

        // Act
        String result = accountService.cancelSubscription("testuser", "wrongpass");

        // Assert
        assertEquals("Invalid password!", result);
    }

    @Test
    void testCancelSubscription_NoActiveSubscription() {
        // Arrange
        UserEntity user = createTestUser("testuser", "password", 0, null);
        when(userRepository.findByUsernameContaining("testuser")).thenReturn(Collections.singletonList(user));

        // Act
        String result = accountService.cancelSubscription("testuser", "password");

        // Assert
        assertEquals("You do not have a subscription!", result);
    }

    @Test
    void testCancelSubscription_ExpiredSubscription() {
        // Arrange
        Date pastDate = Date.from(LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        UserEntity user = createTestUser("testuser", "password", 1, pastDate);
        when(userRepository.findByUsernameContaining("testuser")).thenReturn(Collections.singletonList(user));

        // Act
        String result = accountService.cancelSubscription("testuser", "password");

        // Assert
        assertEquals("You do not have a subscription!", result);
    }

    @Test
    void testCancelSubscription_Success() {
        // Arrange - Less than 1 month remaining (29 days)
        Date futureDate = Date.from(LocalDate.now().plusDays(29).atStartOfDay(ZoneId.systemDefault()).toInstant());
        UserEntity user = createTestUser("testuser", "password", 1, futureDate);
        when(userRepository.findByUsernameContaining("testuser")).thenReturn(Collections.singletonList(user));

        // Act
        String result = accountService.cancelSubscription("testuser", "password");

        // Assert
        assertEquals("You canceled your subscription.", result);
        assertNull(user.getExpirationDate());
        assertEquals(0, user.getTier());
        
        // Verify notifications were added and saved
        assertNotNull(user.getNotifications());
        assertFalse(user.getNotifications().isEmpty());
        verify(userRepository, atLeastOnce()).save(user);
    }

    @Test
    void testCancelSubscription_EarlyCancellation() {
        // Arrange - Set expiration date to exactly 1 month + 1 day from now
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, 1);  // 1 month and 1 day in future
        UserEntity user = createTestUser("testuser", "password", 1, cal.getTime());
        
        when(userRepository.findByUsernameContaining("testuser")).thenReturn(Collections.singletonList(user));

        // Act
        String result = accountService.cancelSubscription("testuser", "password");
        
        // Debug output
        System.out.println("Current date: " + new Date());
        System.out.println("Expiration date: " + user.getExpirationDate());
        System.out.println("Result message: " + result);

        // Assert
        assertEquals("You canceled your subscription and paid extra for early cancellation.", result);
        assertNull(user.getExpirationDate());
        assertEquals(0, user.getTier());
        
        // Verify penalty notification was added
        assertTrue(user.getNotifications().stream()
            .anyMatch(n -> n.getMessage().contains("paid extra")));
    }

    // *** IOAN ***

    @Test
    void testGetAccount_invalidUsername(){
        when(userRepository.findByUsernameContaining("unknown")).thenReturn(Collections.emptyList());

        Account account = accountService.getAccount("unknown", "password");

        assertNotNull(account);
        assertEquals("Invalid username!", account.getStatus());
    }

    @Test
    void testGetAccount_invalidPassword(){
        UserEntity user = createTestUser("username", "password", 0, null);

        when(userRepository.findByUsernameContaining("username")).thenReturn(new ArrayList<UserEntity>(Arrays.asList(user)));

        Account account = accountService.getAccount("username", "notPassword");

        assertNotNull(account);
        assertEquals("Invalid password!", account.getStatus());
    }

    @Test
    void testGetAccount_noExpDate(){
        UserEntity user = createTestUser("username", "password", 0, null);

        when(userRepository.findByUsernameContaining("username")).thenReturn(new ArrayList<UserEntity>(Arrays.asList(user)));

        Account account = accountService.getAccount("username", "password");

        assertNotNull(account);
        assertEquals("Not yet purchased a subscription!", account.getStatus());
        assertEquals(null, account.getGames());
    }

    @Test
    void testGetAccount_expired(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -2);

        UserEntity user = createTestUser("username", "password", 0, calendar.getTime());

        when(userRepository.findByUsernameContaining("username")).thenReturn(new ArrayList<UserEntity>(Arrays.asList(user)));

        Account account = accountService.getAccount("username", "password");

        assertNotNull(account);
        assertEquals("Subscription expired!", account.getStatus());
        assertEquals(null, account.getGames());
    }

    @Test
    void testGetAccount_allOk(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 2);

        UserEntity user = createTestUser("username", "password", 3, calendar.getTime());
        Game game = new Game("1", "Balatro", 1); 

        when(userRepository.findByUsernameContaining("username")).thenReturn(new ArrayList<UserEntity>(Arrays.asList(user)));
        when(gamesService.getGamesAvailable(3)).thenReturn(new ArrayList<Game>(Arrays.asList(game)));

        Account account = accountService.getAccount("username", "password");

        assertNotNull(account);
        assertEquals("Logged in!", account.getStatus());
        assertNotNull(account.getGames());
        assertEquals(1, account.getGames().size());
    }

    @Test
    void testUpgradeTier_invalidTier(){
        List<SubscriptionEntity> subscriptions = Arrays.asList(
            new SubscriptionEntity(1, 90),   // Tier 1
            new SubscriptionEntity(2, 115),  // Current tier (tier 2)
            new SubscriptionEntity(3, 150)   // Tier 3
        );

        UserEntity user = createTestUser("username", "password", 0, null);

        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        when(userRepository.findByUsernameContaining("username")).thenReturn(new ArrayList<>(Arrays.asList(user)));

        String response = accountService.upgradeTier("username", "password", 10);

        assertNotNull(response);
        assertEquals("Tier not found!", response);
    }

    @Test
    void testUpgradeTier_ownedTier(){
        List<SubscriptionEntity> subscriptions = Arrays.asList(
            new SubscriptionEntity(1, 90),   // Tier 1
            new SubscriptionEntity(2, 115),  // Current tier (tier 2)
            new SubscriptionEntity(3, 150)   // Tier 3
        );

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 2);

        UserEntity user = createTestUser("username", "password", 2, calendar.getTime());

        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        when(userRepository.findByUsernameContaining("username")).thenReturn(new ArrayList<>(Arrays.asList(user)));

        String response = accountService.upgradeTier("username", "password", 2);

        assertNotNull(response);
        assertEquals("You already own tier 2!", response);
    }

    @Test
    void testUpgradeTier_upgrades(){
        List<SubscriptionEntity> subscriptions = Arrays.asList(
            new SubscriptionEntity(1, 90),
            new SubscriptionEntity(2, 115),
            new SubscriptionEntity(3, 150)
        );

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 2);

        UserEntity user = createTestUser("username", "password", 0, calendar.getTime());

        when(subscriptionRepository.findAll()).thenReturn(subscriptions);
        when(userRepository.findByUsernameContaining("username")).thenReturn(new ArrayList<>(Arrays.asList(user)));

        String response = accountService.upgradeTier("username", "password", 2);

        assertNotNull(response);
        //TODO complete here
        assertEquals("Purchased tier 2 for 92$ (Original: 115$)", response);
    }

}

