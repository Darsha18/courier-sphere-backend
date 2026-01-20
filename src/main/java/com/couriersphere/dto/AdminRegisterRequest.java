package com.couriersphere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegisterRequest {

    
	@Email(message = "Invalid email format")
    @NotBlank
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    
    public String getEmail() { return email; }
    
    public String getPassword() {return password ;}
	
}
