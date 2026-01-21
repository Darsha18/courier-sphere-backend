package com.couriersphere.dto;

public class CustomerCourierCompanyResponse {

    private Long id;
    private String companyName;
    private String city;
    private String state;
    private String country;
    private String contact;

    public CustomerCourierCompanyResponse(
            Long id,
            String companyName,
            String city,
            String state,
            String country,
            String contact) {

        this.id = id;
        this.companyName = companyName;
        this.city = city;
        this.state = state;
        this.country = country;
        this.contact = contact;
    }

    public Long getId() { return id; }
    public String getCompanyName() { return companyName; }
    public String getCity() { return city; }
    public String getState() { return state; }
    public String getCountry() { return country; }
    public String getContact() { return contact; }
}
