package ro.unibuc.prodeng.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.unibuc.prodeng.exception.EntityNotFoundException;
import ro.unibuc.prodeng.model.CartEntity;
import ro.unibuc.prodeng.model.ComponentEntity;
import ro.unibuc.prodeng.repository.CartRepository;
import ro.unibuc.prodeng.repository.ComponentRepository;
import ro.unibuc.prodeng.repository.UserRepository;
import ro.unibuc.prodeng.request.AddToCartRequest;
import ro.unibuc.prodeng.response.CartItemResponse;
import ro.unibuc.prodeng.response.CartResponse;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired private CartRepository cartRepository;
    @Autowired private ComponentRepository componentRepository;
    @Autowired private UserRepository userRepository;

    public CartResponse getActiveCart(String userId) throws EntityNotFoundException {
        userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        CartEntity cart = cartRepository.findByUserIDAndStatus(userId, CartEntity.CartStatus.OPEN)
                .orElseGet(() -> createNewCart(userId));
        return toResponse(cart);
    }

    public CartResponse addToCart(String userId, AddToCartRequest request) throws EntityNotFoundException {
        ComponentEntity component = componentRepository.findById(request.componentId())
                .orElseThrow(() -> new EntityNotFoundException("Component not found"));

        if (component.getAvailableQuantity() < request.quantity()) {
            throw new IllegalArgumentException("Not enough components in stock.");
        }

        CartEntity cart = cartRepository.findByUserIDAndStatus(userId, CartEntity.CartStatus.OPEN)
                .orElseGet(() -> createNewCart(userId));

        Optional<CartEntity.CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getComponentId().equals(request.componentId())).findFirst();

        if (existingItem.isPresent()) {
            int newQuantity = existingItem.get().getQuantity() + request.quantity();
            if (component.getAvailableQuantity() < newQuantity) {
                throw new IllegalArgumentException("Cannot add more. Exceeds available stock.");
            }
            existingItem.get().setQuantity(newQuantity);
        } else {
            cart.getItems().add(new CartEntity.CartItem(request.componentId(), request.quantity()));
        }

        return toResponse(cartRepository.save(cart));
    }

    public CartResponse removeFromCart(String userId, String componentId) throws EntityNotFoundException {
        CartEntity cart = cartRepository.findByUserIDAndStatus(userId, CartEntity.CartStatus.OPEN)
                .orElseThrow(() -> new EntityNotFoundException("No active cart found"));

        boolean removed = cart.getItems().removeIf(item -> item.getComponentId().equals(componentId));
        if (!removed) throw new EntityNotFoundException("Component not found in cart");

        return toResponse(cartRepository.save(cart));
    }

    public CartResponse submitCart(String userId) throws EntityNotFoundException {
        CartEntity cart = cartRepository.findByUserIDAndStatus(userId, CartEntity.CartStatus.OPEN)
                .orElseThrow(() -> new EntityNotFoundException("No active cart found"));

        if (cart.getItems().isEmpty()) throw new IllegalArgumentException("Cart is empty.");

        for (CartEntity.CartItem item : cart.getItems()) {
            ComponentEntity component = componentRepository.findById(item.getComponentId())
                    .orElseThrow(() -> new EntityNotFoundException("Component not found"));
            
            int remainingStock = component.getAvailableQuantity() - item.getQuantity();
            if (remainingStock < 0) throw new IllegalArgumentException("Component out of stock!");
            
            component.setAvailableQuantity(remainingStock);
            componentRepository.save(component);
        }

        cart.setStatus(CartEntity.CartStatus.SUBMITTED);
        return toResponse(cartRepository.save(cart));
    }

    private CartEntity createNewCart(String userId) {
        CartEntity newCart = new CartEntity();
        newCart.setUserID(userId);
        newCart.setStatus(CartEntity.CartStatus.OPEN);
        return cartRepository.save(newCart);
    }

    private CartResponse toResponse(CartEntity entity) {
        List<CartItemResponse> items = entity.getItems().stream().map(item -> {
            String compName = componentRepository.findById(item.getComponentId())
                    .map(ComponentEntity::getName).orElse("Unknown");
            return new CartItemResponse(item.getComponentId(), compName, item.getQuantity());
        }).toList();

        return new CartResponse(entity.getId(), entity.getUserID(), items, entity.getStatus().name(), entity.getCreatedAt());
    }
}