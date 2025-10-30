package com.graduationproejct.smartfactory.service.auth;

import com.graduationproejct.smartfactory.domain.dto.SignUpRequest;
import com.graduationproejct.smartfactory.domain.dto.UserDto;

public interface AuthService {
    void createAdminAccount();
    UserDto createUser(SignUpRequest request);
}
