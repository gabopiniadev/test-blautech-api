package org.testing.blautech.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.testing.blautech.api.models.Cart;

import java.util.Optional;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUser_IdAndCheckout(Long userId, boolean checkout);
    List<Cart> findAllByUser_IdAndCheckoutTrue(Long userId);
}
