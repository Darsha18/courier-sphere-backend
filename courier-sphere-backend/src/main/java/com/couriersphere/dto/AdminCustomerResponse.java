package com.couriersphere.dto;

public class AdminCustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String contact;
    private String address;
    private String customerRefId;

    public AdminCustomerResponse(
            Long id,
            String firstName,
            String lastName,
            String email,
            String contact,
            String address,
            String customerRefId) {

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.customerRefId = customerRefId;
    }

    public Long getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getContact() { return contact; }
    public String getAddress() { return address; }
    public String getCustomerRefId() { return customerRefId; }
}
