package com.couriersphere.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "courier_companies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourierCompany {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
   // private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;
    private String contact;

    private String street;
    private String landmark;
    private String city;
    private String pincode;
    private String state;
    private String country;

    private LocalDateTime createdAt;

    @PrePersist
    void onCreate() {
        createdAt = LocalDateTime.now();
    }



    // getters & setters
}
