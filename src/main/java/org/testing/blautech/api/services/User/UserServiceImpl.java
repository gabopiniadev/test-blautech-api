package org.testing.blautech.api.services.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.testing.blautech.api.dto.RegisterUserRequest;
import org.testing.blautech.api.exception.ResourceNotFoundException;
import org.testing.blautech.api.models.User;
import org.testing.blautech.api.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegisterUserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceNotFoundException("User with email " + request.getEmail() + " already exists");
        }

        User user = new User();
        user.setName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMailingAddress(request.getAddress());
        user.setDateBirth(request.getBirthDate());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}

