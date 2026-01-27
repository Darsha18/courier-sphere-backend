package com.couriersphere.dto;

public class AdminResponse {

    private Long id;
    private String email;

    public AdminResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
