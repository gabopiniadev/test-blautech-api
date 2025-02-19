package org.testing.blautech.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.testing.blautech.api.models.Cart;
import org.testing.blautech.api.services.cart.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<String> addProductToCart(
            @PathVariable Long userId,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        cartService.addProductToCart(userId, productId, quantity);
        return ResponseEntity.ok("Product added to cart successfully.");
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<String> removeProductFromCart(
            @PathVariable Long userId,
            @RequestParam Long productId
    ) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok("Product removed from cart successfully.");
    }

    @PostMapping("/{userId}/checkout")
    public ResponseEntity<String> checkout(@PathVariable Long userId) {
        cartService.checkout(userId);
        return ResponseEntity.ok("Checkout completed. Cart has been cleared.");
    }
}

