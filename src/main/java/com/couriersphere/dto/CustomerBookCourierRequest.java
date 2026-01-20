package com.couriersphere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerBookCourierRequest {

    private Long courierCompanyId;

    private String courierType;
    private double weight;

    private String receiverName;
    private String receiverAddress;

    // getters & setters
}
