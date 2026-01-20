package com.couriersphere.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "couriers")
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

    @ManyToOne
    private Customer customer;

    public Courier(Long id, String trackingNumber, String courierName, String courierType, double weight,
			String senderName, String receiverName, String receiverAddress, String status, LocalDate courierDate,
			Customer customer, CourierCompany courierCompany, DeliveryPerson deliveryPerson) {
		super();
		this.id = id;
		this.trackingNumber = trackingNumber;
		this.courierName = courierName;
		this.courierType = courierType;
		this.weight = weight;
		this.senderName = senderName;
		this.receiverName = receiverName;
		this.receiverAddress = receiverAddress;
		this.status = status;
		this.courierDate = courierDate;
		this.customer = customer;
		this.courierCompany = courierCompany;
		this.deliveryPerson = deliveryPerson;
	}

	public Courier() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public String getCourierName() {
		return courierName;
	}

	public void setCourierName(String courierName) {
		this.courierName = courierName;
	}

	public String getCourierType() {
		return courierType;
	}

	public void setCourierType(String courierType) {
		this.courierType = courierType;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getCourierDate() {
		return courierDate;
	}

	public void setCourierDate(LocalDate courierDate) {
		this.courierDate = courierDate;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public CourierCompany getCourierCompany() {
		return courierCompany;
	}

	public void setCourierCompany(CourierCompany courierCompany) {
		this.courierCompany = courierCompany;
	}

	public DeliveryPerson getDeliveryPerson() {
		return deliveryPerson;
	}

	public void setDeliveryPerson(DeliveryPerson deliveryPerson) {
		this.deliveryPerson = deliveryPerson;
	}

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
