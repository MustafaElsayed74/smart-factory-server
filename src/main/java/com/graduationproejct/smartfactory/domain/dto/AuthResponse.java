package com.graduationproejct.smartfactory.domain.dto;

import com.graduationproejct.smartfactory.domain.enums.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class AuthResponse {

    private String jwt;

    private UUID userId;

    private UserRole userRole;}
