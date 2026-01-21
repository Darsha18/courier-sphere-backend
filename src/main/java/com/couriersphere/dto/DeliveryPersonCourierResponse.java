package com.couriersphere.dto;

public class DeliveryPersonCourierResponse {

    private Long courierId;
    private String trackingNumber;
    private String courierType;
    private double weight;
    private String receiverName;
    private String receiverAddress;
    private String status;

    public DeliveryPersonCourierResponse(
            Long courierId,
            String trackingNumber,
            String courierType,
            double weight,
            String receiverName,
            String receiverAddress,
            String status) {

        this.courierId = courierId;
        this.trackingNumber = trackingNumber;
        this.courierType = courierType;
        this.weight = weight;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.status = status;
    }

    public Long getCourierId() { return courierId; }
    public String getTrackingNumber() { return trackingNumber; }
    public String getCourierType() { return courierType; }
    public double getWeight() { return weight; }
    public String getReceiverName() { return receiverName; }
    public String getReceiverAddress() { return receiverAddress; }
    public String getStatus() { return status; }
}
