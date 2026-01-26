package com.couriersphere.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "delivery_persons")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DeliveryPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

	private String password;
    private String contact;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CourierCompany courierCompany;

    // getters & setters
}
