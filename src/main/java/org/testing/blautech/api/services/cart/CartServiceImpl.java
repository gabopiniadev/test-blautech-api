package org.testing.blautech.api.services.cart;

import org.springframework.stereotype.Service;
import org.testing.blautech.api.exception.ResourceNotFoundException;
import org.testing.blautech.api.models.Cart;
import org.testing.blautech.api.models.CartItem;
import org.testing.blautech.api.models.Product;
import org.testing.blautech.api.repository.CartRepository;
import org.testing.blautech.api.repository.ProductRepository;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return cartRepository.findCartByUser_Id(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found for userId: " + userId));
    }

    @Override
    public void addProductToCart(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem(null, product, quantity);
            cart.getCartItems().add(newItem);
        }

        cartRepository.save(cart);
    }


    @Override
    public void removeProductFromCart(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);
    }

    @Override
    public void checkout(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }

}
