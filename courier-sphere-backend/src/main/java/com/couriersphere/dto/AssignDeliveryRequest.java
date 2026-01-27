package com.couriersphere.dto;

public class AssignDeliveryRequest {

    private Long courierId;
    private Long deliveryPersonId;
	public Long getCourierId() {
		return courierId;
	}
	public void setCourierId(Long courierId) {
		this.courierId = courierId;
	}
	public Long getDeliveryPersonId() {
		return deliveryPersonId;
	}
	public void setDeliveryPersonId(Long deliveryPersonId) {
		this.deliveryPersonId = deliveryPersonId;
	}

    // getters & setters
}
