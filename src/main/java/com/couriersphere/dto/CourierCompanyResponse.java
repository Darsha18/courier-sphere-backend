package com.couriersphere.dto;

public class CourierCompanyResponse {

    private Long id;
    private String companyName;
    private String email;

    public CourierCompanyResponse(
            Long id,
            String companyName,
            String email) {

        this.id = id;
        this.companyName = companyName;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }
}
