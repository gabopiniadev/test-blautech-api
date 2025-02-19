package org.testing.blautech.api.services.cart;

import org.testing.blautech.api.models.Cart;

public interface CartService {
    Cart getCartByUserId(Long userId);
    void addProductToCart(Long userId, Long productId, int quantity);
    void removeProductFromCart(Long userId, Long productId);
    void checkout(Long userId);
}

