package com.couriersphere.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.couriersphere.enums.PaymentStatus;
import com.couriersphere.enums.CourierStatus;
import com.couriersphere.enums.CourierCategory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@AllArgsConstructor
@NoArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private CourierCategory courierCategory;
    
    private double weight;
    private String receiverName;
	private String receiverAddress;
	
	@Enumerated(EnumType.STRING)
    private CourierStatus status;

    private LocalDate courierDate;
    private String deliveryMessage;

    private LocalDate deliveryDate;
    private LocalTime deliveryTime;
    
    private String razorpayOrderId;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;// e.g., PENDING, PAID
    private Double amount;

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
        status = CourierStatus.BOOKED;
    }

}
