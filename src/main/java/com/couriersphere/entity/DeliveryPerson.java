package com.couriersphere.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "delivery_persons")
public class DeliveryPerson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    public DeliveryPerson(Long id, String firstName, String lastName, String email, String password, String contact,
			boolean active, CourierCompany courierCompany) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.contact = contact;
		this.active = active;
		this.courierCompany = courierCompany;
	}

	public DeliveryPerson() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CourierCompany getCourierCompany() {
		return courierCompany;
	}

	public void setCourierCompany(CourierCompany courierCompany) {
		this.courierCompany = courierCompany;
	}

	private String password;
    private String contact;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CourierCompany courierCompany;

    // getters & setters
}
