package com.couriersphere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyAddDeliveryPersonRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String contact;

    // getters & setters
}
