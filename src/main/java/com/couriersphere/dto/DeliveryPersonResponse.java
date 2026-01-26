package com.couriersphere.dto;

public class DeliveryPersonResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private boolean active;

    public DeliveryPersonResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            String contact,
            boolean active) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.active = active;
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
}
