package ro.unibuc.prodeng.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.model.CartEntity;
import ro.unibuc.prodeng.model.ComponentEntity;
import ro.unibuc.prodeng.model.UserEntity;
import ro.unibuc.prodeng.repository.CartRepository;
import ro.unibuc.prodeng.repository.ComponentRepository;
import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.request.AddToCartRequest;
import ro.unibuc.prodeng.response.CartResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock private CartRepository cartRepository;
    @Mock private ComponentRepository componentRepository;
    @Mock private UserRepository userRepository;

    @InjectMocks private CartService cartService; // Clasa pe care o testăm real

    @Test
    void getActiveCart_ReturnsExistingCart_WhenUserExists() throws EntityNotFoundException {
        // ARRANGE (Pregătirea datelor)
        String userId = "user123";
        UserEntity mockUser = new UserEntity(userId, "Alex", "alex@test.com", "331", false);
        CartEntity mockCart = new CartEntity();
        mockCart.setUserID(userId);
        mockCart.setStatus(CartEntity.CartStatus.OPEN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(cartRepository.findByUserIDAndStatus(userId, CartEntity.CartStatus.OPEN)).thenReturn(Optional.of(mockCart));

        // ACT (Executarea metodei)
        CartResponse response = cartService.getActiveCart(userId);

        // ASSERT (Verificarea rezultatului)
        assertNotNull(response);
        assertEquals(userId, response.userID());
        assertEquals("OPEN", response.status());
    }

    @Test
    void addToCart_AddsNewItem_WhenStockIsSufficient() throws EntityNotFoundException {
        // ARRANGE
        String userId = "user123";
        String compId = "comp1";
        AddToCartRequest request = new AddToCartRequest(compId, 2);

        ComponentEntity mockComponent = new ComponentEntity();
        mockComponent.setId(compId);
        mockComponent.setName("Arduino");
        mockComponent.setAvailableQuantity(5); // Avem destul stoc

        CartEntity mockCart = new CartEntity();
        mockCart.setUserID(userId);
        mockCart.setStatus(CartEntity.CartStatus.OPEN);

        when(componentRepository.findById(compId)).thenReturn(Optional.of(mockComponent));
        when(cartRepository.findByUserIDAndStatus(userId, CartEntity.CartStatus.OPEN)).thenReturn(Optional.of(mockCart));
        when(cartRepository.save(any(CartEntity.class))).thenReturn(mockCart);

        // ACT
        CartResponse response = cartService.addToCart(userId, request);

        // ASSERT
        assertNotNull(response);
        verify(cartRepository, times(1)).save(any(CartEntity.class)); // Verificăm că s-a salvat
    }

    @Test
    void addToCart_ThrowsException_WhenStockIsNotSufficient() {
        // ARRANGE
        String userId = "user123";
        String compId = "comp1";
        AddToCartRequest request = new AddToCartRequest(compId, 10); // Cerem 10

        ComponentEntity mockComponent = new ComponentEntity();
        mockComponent.setId(compId);
        mockComponent.setAvailableQuantity(5); // Avem doar 5

        when(componentRepository.findById(compId)).thenReturn(Optional.of(mockComponent));

        // ACT & ASSERT (Testăm că dă eroare)
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            cartService.addToCart(userId, request);
        });
        assertEquals("Not enough components in stock.", exception.getMessage());
    }

    @Test
    void submitCart_UpdatesStockAndChangesStatus_WhenCartIsValid() throws EntityNotFoundException {
        // ARRANGE
        String userId = "user123";
        CartEntity mockCart = new CartEntity();
        mockCart.setUserID(userId);
        mockCart.setStatus(CartEntity.CartStatus.OPEN);
        mockCart.getItems().add(new CartEntity.CartItem("comp1", 2)); // 2 piese în coș

        ComponentEntity mockComponent = new ComponentEntity();
        mockComponent.setId("comp1");
        mockComponent.setAvailableQuantity(5);

        when(cartRepository.findByUserIDAndStatus(userId, CartEntity.CartStatus.OPEN)).thenReturn(Optional.of(mockCart));
        when(componentRepository.findById("comp1")).thenReturn(Optional.of(mockComponent));
        when(cartRepository.save(any(CartEntity.class))).thenReturn(mockCart);

        // ACT
        CartResponse response = cartService.submitCart(userId);

        // ASSERT
        assertEquals("SUBMITTED", mockCart.getStatus().name());
        assertEquals(3, mockComponent.getAvailableQuantity()); // 5 - 2 = 3 (stoc scăzut)
        verify(componentRepository, times(1)).save(mockComponent); // S-a salvat piesa
        verify(cartRepository, times(1)).save(mockCart); // S-a salvat coșul
    }
}