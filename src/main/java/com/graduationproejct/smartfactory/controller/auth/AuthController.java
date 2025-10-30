package com.graduationproejct.smartfactory.controller.auth;


import com.graduationproejct.smartfactory.domain.dto.AuthRequest;
import com.graduationproejct.smartfactory.domain.dto.AuthResponse;
import com.graduationproejct.smartfactory.domain.dto.SignUpRequest;
import com.graduationproejct.smartfactory.domain.dto.UserDto;
import com.graduationproejct.smartfactory.domain.entity.User;
import com.graduationproejct.smartfactory.repository.UserRepository;
import com.graduationproejct.smartfactory.service.auth.AuthService;
import com.graduationproejct.smartfactory.service.jwt.UserService;
import com.graduationproejct.smartfactory.uill.JwtUtil;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signInUser(@RequestBody SignUpRequest request) {
        try {
            UserDto createdUser = authService.createUser(request);
            return ResponseEntity.ok(createdUser);
        } catch (EntityExistsException ex) {
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public AuthResponse createAuthenticationToken(@RequestBody AuthRequest request) {
        // Checks if the input credentials are correct and matched
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect username or password.");
        }

        // Create Token - Login

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getEmail());
        Optional<User> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthResponse authResponse = new AuthResponse();
        if (optionalUser.isPresent()) {
            authResponse.setJwt(jwt);
            authResponse.setUserRole(optionalUser.get().getUserRole());
            authResponse.setUserId(optionalUser.get().getId());
        }
        return authResponse;
    }}
