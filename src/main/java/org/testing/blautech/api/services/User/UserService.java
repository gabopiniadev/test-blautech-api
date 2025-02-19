package org.testing.blautech.api.services.User;

import org.testing.blautech.api.dto.RegisterUserRequest;
import org.testing.blautech.api.models.User;

public interface UserService {
    void registerUser(RegisterUserRequest request);
    User findByEmail(String email);
}

