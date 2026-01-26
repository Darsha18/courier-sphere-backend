package com.couriersphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryPersonDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private boolean active;
    private String courierCompanyName;

   
}
