package com.couriersphere.dto;

import com.couriersphere.enums.CourierStatus;

public class DeliveryStatusUpdateRequest {

    private CourierStatus status;
    private String deliveryMessage;

    public CourierStatus getStatus() {
        return status;
    }

    public void setStatus(CourierStatus status) {
        this.status = status;
    }

    public String getDeliveryMessage() {
        return deliveryMessage;
    }

    public void setDeliveryMessage(String deliveryMessage) {
        this.deliveryMessage = deliveryMessage;
    }
}
