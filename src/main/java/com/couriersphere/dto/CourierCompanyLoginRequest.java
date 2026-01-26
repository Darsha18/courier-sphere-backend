package com.couriersphere.dto;

public class CourierCompanyLoginRequest {

    private String email;
    private String password;

    public CourierCompanyLoginRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
