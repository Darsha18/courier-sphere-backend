package com.couriersphere.service;

import com.couriersphere.dto.*;
import com.couriersphere.entity.*;
import com.couriersphere.repository.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CourierCompanyServiceImpl implements CourierCompanyService {

    private final CourierCompanyRepository companyRepo;
    private final DeliveryPersonRepository deliveryRepo;
    private final CustomerRepository customerRepo;
    private final CourierRepository courierRepo;

    public CourierCompanyServiceImpl(
            CourierCompanyRepository companyRepo,
            DeliveryPersonRepository deliveryRepo,
            CustomerRepository customerRepo,
            CourierRepository courierRepo) {

        this.companyRepo = companyRepo;
        this.deliveryRepo = deliveryRepo;
        this.customerRepo = customerRepo;
        this.courierRepo = courierRepo;
    }

    @Override
    public ApiResponse<?> registerDeliveryPerson(Long companyId, DeliveryPersonRegisterRequest request) {

        CourierCompany company = companyRepo.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Courier company not found"));

        DeliveryPerson dp = new DeliveryPerson();
        dp.setFirstName(request.getFirstName());
        dp.setLastName(request.getLastName());
        dp.setEmail(request.getEmail());
        dp.setPassword(request.getPassword());
        dp.setContact(request.getContact());
        dp.setCourierCompany(company);

        deliveryRepo.save(dp);

        return new ApiResponse<>(true, "Delivery person registered", null);
    }

    @Override
    public ApiResponse<?> addCustomerCourier(Long companyId, AddCourierRequest request) {

        CourierCompany company = companyRepo.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Courier company not found"));

        Customer customer = customerRepo.findByCustomerRefId(request.getCustomerRefId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Courier courier = new Courier();
        courier.setCourierName(request.getCourierName());
        courier.setCourierType(request.getCourierType());
        courier.setWeight(request.getWeight());
        courier.setReceiverName(request.getReceiverName());
        courier.setReceiverAddress(request.getReceiverAddress());
        courier.setCustomer(customer);
        courier.setCourierCompany(company);

        courierRepo.save(courier);

        return new ApiResponse<>(true, "Courier added successfully", null);
    }

    @Override
    public ApiResponse<?> assignDelivery(AssignDeliveryRequest request) {

        Courier courier = courierRepo.findById(request.getCourierId())
                .orElseThrow(() -> new RuntimeException("Courier not found"));

        DeliveryPerson dp = deliveryRepo.findById(request.getDeliveryPersonId())
                .orElseThrow(() -> new RuntimeException("Delivery person not found"));

        courier.setDeliveryPerson(dp);
        courier.setTrackingNumber("TRK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        courier.setStatus("ASSIGNED");

        courierRepo.save(courier);

        return new ApiResponse<>(true, "Courier assigned to delivery person", null);
    }

    @Override
    public ApiResponse<?> getDeliveryPersons(Long companyId) {
        return new ApiResponse<>(true, "Delivery persons list",
                deliveryRepo.findByCourierCompanyId(companyId));
    }
    
    @Override
    public ApiResponse<String> addDeliveryPerson(
            Long companyId,
            CompanyAddDeliveryPersonRequest request) {

        CourierCompany company = companyRepo.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Courier company not found"));

        DeliveryPerson dp = new DeliveryPerson();
        dp.setFirstName(request.getFirstName());
        dp.setLastName(request.getLastName());
        dp.setEmail(request.getEmail());
        dp.setPassword(request.getPassword());
        dp.setContact(request.getContact());
        dp.setCourierCompany(company);
        dp.setActive(true);

        deliveryRepo.save(dp);

        return new ApiResponse<>(
                true,
                "Delivery person added successfully",
                null
        );
    }
    
    @Override
    public ApiResponse<String> deleteDeliveryPerson(
            Long companyId,
            Long deliveryPersonId) {

        DeliveryPerson dp = deliveryRepo.findById(deliveryPersonId)
                .orElseThrow(() -> new RuntimeException("Delivery person not found"));

        // 🔒 Ensure same company
        if (!dp.getCourierCompany().getId().equals(companyId)) {
            throw new RuntimeException("Unauthorized delivery person access");
        }

        boolean assigned =
                courierRepo.existsByDeliveryPersonId(deliveryPersonId);

        if (assigned) {
            throw new RuntimeException(
                    "Cannot delete delivery person with assigned couriers");
        }

        deliveryRepo.delete(dp);

        return new ApiResponse<>(
                true,
                "Delivery person deleted successfully",
                null
        );
    }


}
