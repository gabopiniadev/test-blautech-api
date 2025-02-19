package org.testing.blautech.api.repository;

import org.springframework.stereotype.Repository;
import org.testing.blautech.api.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}

