package ro.unibuc.prodeng.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.request.AddToCartRequest;
import ro.unibuc.prodeng.response.CartResponse;
import ro.unibuc.prodeng.service.CartService;

@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getActiveCart(@PathVariable String userId) throws EntityNotFoundException {
        CartResponse cart = cartService.getActiveCart(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartResponse> addToCart(
            @PathVariable String userId,
            @Valid @RequestBody AddToCartRequest request) throws EntityNotFoundException {
        
        CartResponse cart = cartService.addToCart(userId, request);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/submit")
    public ResponseEntity<CartResponse> submitCart(@PathVariable String userId) throws EntityNotFoundException {
        CartResponse cart = cartService.submitCart(userId);
        return ResponseEntity.ok(cart);
    }
}