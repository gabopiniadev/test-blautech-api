package org.testing.blautech.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.testing.blautech.api.models.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

