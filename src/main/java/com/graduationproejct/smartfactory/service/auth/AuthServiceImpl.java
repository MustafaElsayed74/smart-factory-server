package com.graduationproejct.smartfactory.service.auth;

import com.graduationproejct.smartfactory.domain.dto.SignUpRequest;
import com.graduationproejct.smartfactory.domain.dto.UserDto;
import com.graduationproejct.smartfactory.domain.entity.User;
import com.graduationproejct.smartfactory.domain.enums.UserRole;
import com.graduationproejct.smartfactory.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;


    @PostConstruct
    @Override
    public void createAdminAccount() {
        Optional<User> adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if (adminAccount.isEmpty()) {
            User user = new User();
            user.setEmail("admin@test.com");
            user.setName("admin");
            user.setUserRole(UserRole.ADMIN);
            user.setPassword(passwordEncoder.encode("admin"));
            userRepository.save(user);
            System.out.println("Admin account created successfully");
        } else {
            System.out.println("Admin account already exists");
        }
    }

    @Override
    public UserDto createUser(SignUpRequest request) {

        if (userRepository.findFirstByEmail(request.getEmail()).isPresent()) {
            throw new EntityExistsException(
                    String.format("User with %s already exists", request.getName())
            );
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setUserRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User createdUser = userRepository.save(user);

        return createdUser.createUserDto();
    }
}
