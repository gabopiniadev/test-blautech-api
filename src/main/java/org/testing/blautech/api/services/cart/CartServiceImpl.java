package org.testing.blautech.api.services.cart;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.testing.blautech.api.exception.ResourceNotFoundException;
import org.testing.blautech.api.models.Cart;
import org.testing.blautech.api.models.CartItem;
import org.testing.blautech.api.models.Product;
import org.testing.blautech.api.models.User;
import org.testing.blautech.api.repository.CartRepository;
import org.testing.blautech.api.repository.ProductRepository;
import org.testing.blautech.api.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void addProductToCart(UserDetails userDetails, Long productId, int quantity) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + userDetails.getUsername()));

        Cart cart = cartRepository.findCartByUser_IdAndCheckout(user.getId(), false)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new ArrayList<>());
                    newCart.setCheckout(false);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getCartItems().add(newItem);
        }

        cartRepository.save(cart);
    }

    @Override
    public void removeProductFromCartByEmail(String email, Long productId) {
        Cart cart = getCartByUserEmail(email);
        cart.getCartItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartRepository.save(cart);

    }

    @Override
    public void checkoutByEmail(String email) {
        Cart cart = getCartByUserEmail(email);
        cart.setCheckout(true);
        cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return cartRepository.findCartByUser_IdAndCheckout(user.getId(), false)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setCartItems(new ArrayList<>());
                    newCart.setCheckout(false);
                    return cartRepository.save(newCart);
                });
    }

    @Override
    public List<Cart> getCompletedOrders(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return cartRepository.findAllByUser_IdAndCheckoutTrue(user.getId());
    }

    @Override
    public int getCartItemCount(String email) {
        Cart cart = getCartByUserEmail(email);
        return cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum();
    }
}
