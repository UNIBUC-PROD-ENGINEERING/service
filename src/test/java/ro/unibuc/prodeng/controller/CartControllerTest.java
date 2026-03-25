package ro.unibuc.prodeng.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.request.AddToCartRequest;
import ro.unibuc.prodeng.response.CartResponse;
import ro.unibuc.prodeng.service.CartService;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartService;

    @InjectMocks
    private CartController cartController;

    @Test
    void getActiveCart_ReturnsOkResponse() throws EntityNotFoundException {
        // ARRANGE
        String userId = "user123";
        CartResponse mockResponse = new CartResponse("cart1", userId, List.of(), "OPEN", Instant.now());
        when(cartService.getActiveCart(userId)).thenReturn(mockResponse);

        // ACT
        ResponseEntity<CartResponse> response = cartController.getActiveCart(userId);

        // ASSERT
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
        verify(cartService, times(1)).getActiveCart(userId);
    }

    @Test
    void addToCart_ReturnsOkResponse() throws EntityNotFoundException {
        // ARRANGE
        String userId = "user123";
        AddToCartRequest request = new AddToCartRequest("comp1", 2);
        CartResponse mockResponse = new CartResponse("cart1", userId, List.of(), "OPEN", Instant.now());
        when(cartService.addToCart(userId, request)).thenReturn(mockResponse);

        // ACT
        ResponseEntity<CartResponse> response = cartController.addToCart(userId, request);

        // ASSERT
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
    }
}