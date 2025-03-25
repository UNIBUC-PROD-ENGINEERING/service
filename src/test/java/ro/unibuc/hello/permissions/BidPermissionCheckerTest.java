package ro.unibuc.hello.permissions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ro.unibuc.hello.data.AuctionEntity;
import ro.unibuc.hello.data.BidEntity;
import ro.unibuc.hello.data.BidRepository;
import ro.unibuc.hello.data.ItemEntity;
import ro.unibuc.hello.data.UserEntity;
import ro.unibuc.hello.exception.EntityNotFoundException;
import ro.unibuc.hello.exception.UnauthorizedException;

public class BidPermissionCheckerTest {

    @Mock
    private BidRepository bidRepository;

    @InjectMocks
    private BidPermissionChecker permissionChecker;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckOwnership_Success() {
        // Arrange
        String id = "1";
        String userId = "11";
        String ownerId = "11";
        UserEntity user1 = new UserEntity(ownerId, "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auction = new AuctionEntity(id, "Auction 1", "Description 1", 100, true, item1, user1);
        BidEntity bid = new BidEntity("31", 10, user1, auction);

        when(bidRepository.findById(id)).thenReturn(Optional.of(bid));

        // Act & Assert
        assertDoesNotThrow(() -> permissionChecker.checkOwnership(userId, id));
    }

    @Test
    void testCheckOwnership_NotFound() {
        // Arrange
        String id = "1";
        String userId = "11";

        when(bidRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> permissionChecker.checkOwnership(userId, id));
    }

    @Test
    void testCheckOwnership_Unauthorized() {
        // Arrange
        String id = "1";
        String userId = "11";
        String ownerId = "12";
        UserEntity user1 = new UserEntity(ownerId, "user 1", "username1", "password1");
        ItemEntity item1 = new ItemEntity("21", "Item 1", "description 1", user1);
        AuctionEntity auction = new AuctionEntity(id, "Auction 1", "Description 1", 100, true, item1, user1);
        BidEntity bid = new BidEntity("31", 10, user1, auction);

        when(bidRepository.findById(id)).thenReturn(Optional.of(bid));

        // Act & Assert
        assertThrows(UnauthorizedException.class, () -> permissionChecker.checkOwnership(userId, id));
    }
}
