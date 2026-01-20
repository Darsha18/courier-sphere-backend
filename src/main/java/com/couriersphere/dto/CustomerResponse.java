package com.couriersphere.dto;

import lombok.Getter;

@Getter
public class CustomerResponse {

    private Long id;
    private String customerRefId;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String address;

    public CustomerResponse(Long id, String customerRefId, String firstName,
                            String lastName, String email,
                            String contact, String address) {
        this.id = id;
        this.customerRefId = customerRefId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.address = address;
    }

    // getters only
}
