package com.couriersphere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCourierCompanyRequest {

    private String fullName;
    //private String lastName;
    private String email;
    private String password;
    private String contact;

    private String street;
    private String landmark;
    private String city;
    private String pincode;
    private String state;
    private String country;

    // getters & setters
}
