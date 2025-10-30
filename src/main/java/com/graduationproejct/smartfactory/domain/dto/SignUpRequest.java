package com.graduationproejct.smartfactory.domain.dto;

import lombok.Data;

@Data
public class SignUpRequest {

    private String email;

    private String password;

    private String name;
}
