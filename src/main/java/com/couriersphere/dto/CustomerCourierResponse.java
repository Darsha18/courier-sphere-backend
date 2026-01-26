package com.couriersphere.dto;

/**
 * DTO for customer's own courier view
 * Contains tracking information, status, and delivery person details
 */
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

    public CustomerCourierResponse(
            Long courierId,
            String trackingNumber,
            String courierType,
            double weight,
            String receiverName,
            String receiverAddress,
            String courierCompanyName,
            String deliveryPersonName,
            String deliveryPersonContact,
            String status,
            String deliveryMessage) {

        this.courierId = courierId;
        this.trackingNumber = trackingNumber;
        this.courierType = courierType;
        this.weight = weight;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.courierCompanyName = courierCompanyName;
        this.deliveryPersonName = deliveryPersonName;
        this.deliveryPersonContact = deliveryPersonContact;
        this.status = status;
        this.deliveryMessage = deliveryMessage;
    }

    // Getters
    public Long getCourierId() { return courierId; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getCourierType() { return courierType; }
    public double getWeight() { return weight; }
    public String getReceiverName() { return receiverName; }
    public String getReceiverAddress() { return receiverAddress; }
    public String getCourierCompanyName() { return courierCompanyName; }
    public String getDeliveryPersonName() { return deliveryPersonName; }
    public String getDeliveryPersonContact() { return deliveryPersonContact; }
    public String getStatus() { return status; }
    public String getDeliveryMessage() { return deliveryMessage; }
}
