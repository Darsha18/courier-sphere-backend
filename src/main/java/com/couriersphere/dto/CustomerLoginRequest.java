package com.couriersphere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerLoginRequest {

    @Email
    private String email;

    @NotBlank
    private String password;

    // getters and setters
}
