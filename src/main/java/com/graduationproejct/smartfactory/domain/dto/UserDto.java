package com.graduationproejct.smartfactory.domain.dto;

import com.graduationproejct.smartfactory.domain.enums.UserRole;
import lombok.Data;

import java.util.UUID;

@Data
public class UserDto {
    private UUID id;

    private String email;

    private String name;

    private UserRole userRole;
}
