package com.couriersphere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminCourierCompanyResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String street;
    private String landmark;
    private String city;
    private String pincode;
    private String state;
    private String country;

    public AdminCourierCompanyResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            String contact,
            String street,
            String landmark,
            String city,
            String pincode,
            String state,
            String country) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.street = street;
        this.landmark = landmark;
        this.city = city;
        this.pincode = pincode;
        this.state = state;
        this.country = country;
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

    public String getStreet() {
        return street;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getCity() {
        return city;
    }

    public String getPincode() {
        return pincode;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }
}
