package com.couriersphere.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCourierDTO {

    private String customerRefId;
    private String courierName;
    private String courierType;
    private double weight;
    private String receiverName;
    private String receiverAddress;
	
    

    // getters & setters
}
