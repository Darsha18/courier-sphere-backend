package com.couriersphere.dto;

public class CompanyCourierResponse {

    private Long courierId;
    private String trackingNumber;
    private Long customerId;

    private String courierType;
    private double weight;

    private String customerName;
    private String customerContact;

    private String receiverName;
    private String receiverAddress;

    private String deliveryPersonName;
    private String deliveryPersonContact;

    private String status;
    private String deliveryMessage;

    public CompanyCourierResponse(
            Long courierId,
            String trackingNumber,
            Long customerId,
            String courierType,
            double weight,
            String customerName,
            String customerContact,
            String receiverName,
            String receiverAddress,
            String deliveryPersonName,
            String deliveryPersonContact,
            String status,
            String deliveryMessage) {

        this.courierId = courierId;
        this.trackingNumber = trackingNumber;
        this.customerId = customerId;
        this.courierType = courierType;
        this.weight = weight;
        this.customerName = customerName;
        this.customerContact = customerContact;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.deliveryPersonName = deliveryPersonName;
        this.deliveryPersonContact = deliveryPersonContact;
        this.status = status;
        this.deliveryMessage = deliveryMessage;
    }

    public Long getCourierId() {
        return courierId;
    }

    public void setCourierId(Long courierId) {
        this.courierId = courierId;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCourierType() {
        return courierType;
    }

    public void setCourierType(String courierType) {
        this.courierType = courierType;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContact() {
        return customerContact;
    }

    public void setCustomerContact(String customerContact) {
        this.customerContact = customerContact;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }

    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }

    public String getDeliveryPersonContact() {
        return deliveryPersonContact;
    }

    public void setDeliveryPersonContact(String deliveryPersonContact) {
        this.deliveryPersonContact = deliveryPersonContact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeliveryMessage() {
        return deliveryMessage;
    }

    public void setDeliveryMessage(String deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }
}
