package com.couriersphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerCourierCompanyResponse {

    private Long id;
    private String companyName;
    private String city;
    private String state;
    private String country;
    private String contact;

    
}
