package org.testing.blautech.api.services.cart;

import org.springframework.security.core.userdetails.UserDetails;
import org.testing.blautech.api.models.Cart;

import java.util.List;

public interface CartService {
    Cart getCartByUserEmail(String email);

    void addProductToCart(UserDetails userDetails, Long productId, int quantity);

    void removeProductFromCartByEmail(String email, Long productId);

    void checkoutByEmail(String email);

    List<Cart> getCompletedOrders(String email);

    int getCartItemCount(String email);
}

