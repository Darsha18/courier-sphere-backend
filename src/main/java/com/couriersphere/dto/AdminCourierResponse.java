package com.couriersphere.dto;

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

    public AdminCourierResponse(
            Long courierId,
            String trackingNumber,
            String courierCompanyName,
            String customerName,
            String customerRefId,
            String courierType,
            double weight,
            String receiverName,
            String receiverAddress,
            String deliveryPersonName,
            String deliveryPersonContact,
            String status) {

        this.courierId = courierId;
        this.trackingNumber = trackingNumber;
        this.courierCompanyName = courierCompanyName;
        this.customerName = customerName;
        this.customerRefId = customerRefId;
        this.courierType = courierType;
        this.weight = weight;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.deliveryPersonName = deliveryPersonName;
        this.deliveryPersonContact = deliveryPersonContact;
        this.status = status;
    }

    public Long getCourierId() { return courierId; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getCourierCompanyName() { return courierCompanyName; }
    public String getCustomerName() { return customerName; }
    public String getCustomerRefId() { return customerRefId; }
    public String getCourierType() { return courierType; }
    public double getWeight() { return weight; }
    public String getReceiverName() { return receiverName; }
    public String getReceiverAddress() { return receiverAddress; }
    public String getDeliveryPersonName() { return deliveryPersonName; }
    public String getDeliveryPersonContact() { return deliveryPersonContact; }
    public String getStatus() { return status; }
}
