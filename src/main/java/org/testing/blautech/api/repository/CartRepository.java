package org.testing.blautech.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.testing.blautech.api.models.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findCartByUser_Id(Long userId);
}
