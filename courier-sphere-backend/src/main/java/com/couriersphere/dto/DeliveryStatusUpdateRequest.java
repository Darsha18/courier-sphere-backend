package com.couriersphere.dto;

public class DeliveryStatusUpdateRequest {

    private String status;           // PICKED_UP, IN_PROCESS, OUT_FOR_DELIVERY, DELIVERED
    private String deliveryMessage;

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
