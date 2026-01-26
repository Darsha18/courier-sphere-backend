package com.couriersphere.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "couriers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String trackingNumber;

    private String courierName;
    private String courierType;
    private double weight;

    private String senderName;
    private String receiverName;
    private String receiverAddress;

    private String status;

    private LocalDate courierDate;
    private String deliveryMessage;

    private LocalDate deliveryDate;
    private LocalTime deliveryTime;


    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;


	@ManyToOne
    private CourierCompany courierCompany;

    @ManyToOne
    private DeliveryPerson deliveryPerson;

    @PrePersist
    void onCreate() {
        courierDate = LocalDate.now();
        status = "BOOKED";
    }

    // getters & setters
}
