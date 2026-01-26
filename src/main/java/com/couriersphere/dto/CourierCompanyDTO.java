package com.couriersphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierCompanyDTO {

    private Long id;
    private String fullName;
    private String email;
    private String contact;
    private String street;
    private String landmark;
    private String city;
    private String pincode;
    private String state;
    private String country;

    
}
