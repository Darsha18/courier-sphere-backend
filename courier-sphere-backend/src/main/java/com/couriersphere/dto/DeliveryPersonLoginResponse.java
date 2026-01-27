package com.couriersphere.dto;

public class DeliveryPersonLoginResponse {

    private Long deliveryPersonId;
    private String name;
    private String companyName;

    public DeliveryPersonLoginResponse(
            Long deliveryPersonId,
            String name,
            String companyName) {

        this.deliveryPersonId = deliveryPersonId;
        this.name = name;
        this.companyName = companyName;
    }

    public Long getDeliveryPersonId() {
        return deliveryPersonId;
    }

    public String getName() {
        return name;
    }

    public String getCompanyName() {
        return companyName;
    }
}
