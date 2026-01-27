package com.couriersphere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerBookCourierRequest {

    private Long courierCompanyId;

    private String courierType;
    private double weight;

    private String receiverName;
    private String receiverAddress;
    private String receiverContact;
    private String receiverPincode;
	public Long getCourierCompanyId() {
		return courierCompanyId;
	}
	public void setCourierCompanyId(Long courierCompanyId) {
		this.courierCompanyId = courierCompanyId;
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
	public String getReceiverContact() {
		return receiverContact;
	}
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}
	public String getReceiverPincode() {
		return receiverPincode;
	}
	public void setReceiverPincode(String receiverPincode) {
		this.receiverPincode = receiverPincode;
	}

    // getters & setters
}
