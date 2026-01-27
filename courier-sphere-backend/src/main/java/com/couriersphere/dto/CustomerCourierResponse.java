package com.couriersphere.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for customer's own courier view
 * Contains tracking information, status, delivery person details, and payment info
 */
@Getter
@AllArgsConstructor
public class CustomerCourierResponse {

    private Long courierId;
    private String trackingNumber;
    private String courierType;
    private double weight;
    private String receiverName;
    private String receiverAddress;
    private String courierCompanyName;
    private String deliveryPersonName;
    private String deliveryPersonContact;
    private String status;
    private String deliveryMessage;
    
    // Added for Razorpay Integration
    private String paymentStatus; 
    private Double amount;
}