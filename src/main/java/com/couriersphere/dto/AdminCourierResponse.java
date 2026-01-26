package com.couriersphere.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminCourierResponse {

    private Long courierId;
    private String trackingNumber;

    private String courierCompanyName;

    private String customerName;
    private String customerRefId;

    private String courierType;
    private double weight;

    private String receiverName;
    private String receiverAddress;

    private String deliveryPersonName;
    private String deliveryPersonContact;

    private String status;

    
}
