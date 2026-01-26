package com.couriersphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDTO {

    private Long id;
    private String customerRefId;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String address;

    

    // getters only
}
