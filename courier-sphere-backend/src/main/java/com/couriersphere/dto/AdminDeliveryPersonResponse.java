package com.couriersphere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminDeliveryPersonResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private boolean active;
    private String courierCompanyName;

    public AdminDeliveryPersonResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            String contact,
            boolean active,
            String courierCompanyName) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.active = active;
        this.courierCompanyName = courierCompanyName;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContact() {
        return contact;
    }

    public boolean isActive() {
        return active;
    }

    public String getCourierCompanyName() {
        return courierCompanyName;
    }
}
