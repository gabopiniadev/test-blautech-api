package org.testing.blautech.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.testing.blautech.api.models.Cart;
import org.testing.blautech.api.services.cart.CartService;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCartByUser(@AuthenticationPrincipal UserDetails userDetails) {
        Cart cart = cartService.getCartByUserEmail(userDetails.getUsername());
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Cart>> getCompletedOrders(@AuthenticationPrincipal UserDetails userDetails) {
        List<Cart> orders = cartService.getCompletedOrders(userDetails.getUsername());
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addProductToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long productId,
            @RequestParam int quantity
    ) {
        cartService.addProductToCart(userDetails, productId, quantity);
        return ResponseEntity.ok("Product added to cart successfully.");
    }

    @DeleteMapping("/remove")
    public ResponseEntity<String> removeProductFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestParam Long productId
    ) {
        cartService.removeProductFromCartByEmail(userDetails.getUsername(), productId);
        return ResponseEntity.ok("Product removed from cart successfully.");
    }

    @GetMapping("/items")
    public ResponseEntity<Integer> getCartItemCount(@AuthenticationPrincipal UserDetails userDetails) {
        int itemCount = cartService.getCartItemCount(userDetails.getUsername());
        return ResponseEntity.ok(itemCount);
    }


    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@AuthenticationPrincipal UserDetails userDetails) {
        cartService.checkoutByEmail(userDetails.getUsername());
        return ResponseEntity.ok("Checkout completed. Cart has been cleared.");
    }

}

