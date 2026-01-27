package com.couriersphere.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRegisterRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    public CustomerRegisterRequest(@NotBlank String firstName, @NotBlank String lastName, @Email String email,
			@NotBlank String password, String contact, String address) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.address = address;
	}
	@NotBlank
    private String password;

    private String contact;
    private String address;
	
    // getters and setters
}
