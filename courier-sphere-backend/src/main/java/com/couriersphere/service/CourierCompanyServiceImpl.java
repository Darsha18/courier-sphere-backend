package com.couriersphere.service;

import com.couriersphere.dto.*;
import com.couriersphere.entity.*;
import com.couriersphere.enums.CourierStatus;
import com.couriersphere.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CourierCompanyServiceImpl implements CourierCompanyService {

    private final CourierCompanyRepository companyRepo;
    private final DeliveryPersonRepository deliveryRepo;
    private final CourierRepository courierRepo;

    public CourierCompanyServiceImpl(
            CourierCompanyRepository companyRepo,
            DeliveryPersonRepository deliveryRepo,
            CourierRepository courierRepo) {

        this.companyRepo = companyRepo;
        this.deliveryRepo = deliveryRepo;
        this.courierRepo = courierRepo;
    }

    @Override
    public ApiResponse<CourierCompanyResponse> login(
            CourierCompanyLoginRequest request) {

        CourierCompany company = companyRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!company.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        CourierCompanyResponse response =
                new CourierCompanyResponse(
                        company.getId(),
                        company.getCompanyName(),
                        company.getEmail()
                );

        return new ApiResponse<>(true, "Login successful", response);
    }

    @Override
    public ApiResponse<?> assignDelivery(AssignDeliveryRequest request) {

        Courier courier = courierRepo.findById(request.getCourierId())
                .orElseThrow(() -> new RuntimeException("Courier not found"));

        DeliveryPerson dp = deliveryRepo.findById(request.getDeliveryPersonId())
                .orElseThrow(() -> new RuntimeException("Delivery person not found"));

        courier.setDeliveryPerson(dp);

        courier.setTrackingNumber(
                "TRK-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase()
        );

        courier.setStatus(CourierStatus.ASSIGNED);

        courierRepo.save(courier);

        return new ApiResponse<>(true, "Courier assigned successfully", null);
    }

    @Override
    public ApiResponse<List<DeliveryPersonResponse>> getDeliveryPersons(
            Long loggedInCompanyId) {

        List<DeliveryPersonResponse> response =
                deliveryRepo.findByCourierCompanyId(loggedInCompanyId)
                        .stream()
                        .map(dp -> new DeliveryPersonResponse(
                                dp.getId(),
                                dp.getFirstName(),
                                dp.getLastName(),
                                dp.getEmail(),
                                dp.getContact(),
                                dp.isActive()
                        ))
                        .toList();

        return new ApiResponse<>(true, "Delivery persons fetched", response);
    }

    @Override
    public ApiResponse<String> addDeliveryPerson(
            Long loggedInCompanyId,
            CompanyAddDeliveryPersonRequest request) {

        CourierCompany company = companyRepo.findById(loggedInCompanyId)
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

        return new ApiResponse<>(true, "Delivery person added successfully", null);
    }

    @Override
    public ApiResponse<String> deleteDeliveryPerson(
            Long loggedInCompanyId,
            Long deliveryPersonId) {

        DeliveryPerson dp = deliveryRepo.findById(deliveryPersonId)
                .orElseThrow(() -> new RuntimeException("Delivery person not found"));

        if (!dp.getCourierCompany().getId().equals(loggedInCompanyId)) {
            throw new RuntimeException("Unauthorized action");
        }

        boolean assigned = courierRepo.existsByDeliveryPersonId(deliveryPersonId);
        if (assigned) {
            throw new RuntimeException("Cannot delete assigned delivery person");
        }

        deliveryRepo.delete(dp);

        return new ApiResponse<>(true, "Delivery person deleted", null);
    }

    @Override
    public ApiResponse<String> updateDeliveryPersonStatus(
            Long loggedInCompanyId,
            Long deliveryPersonId,
            boolean active) {

        DeliveryPerson dp = deliveryRepo.findById(deliveryPersonId)
                .orElseThrow(() -> new RuntimeException("Delivery person not found"));

        if (!dp.getCourierCompany().getId().equals(loggedInCompanyId)) {
            throw new RuntimeException("Unauthorized action");
        }

        dp.setActive(active);
        deliveryRepo.save(dp);

        return new ApiResponse<>(true, "Delivery person status updated", null);
    }

    @Override
    public ApiResponse<List<CompanyCourierResponse>> getCompanyCouriers(
            Long loggedInCompanyId) {

        List<CompanyCourierResponse> response =
                courierRepo.findByCourierCompanyId(loggedInCompanyId)
                        .stream()
                        .map(c -> new CompanyCourierResponse(
                                c.getId(),
                                c.getTrackingNumber(),
                                c.getCustomer().getId(),
                                c.getCourierCategory() != null ? c.getCourierCategory().toString() : "N/A",
                                c.getWeight(),
                                c.getCustomer().getFirstName() + " " +
                                        c.getCustomer().getLastName(),
                                c.getCustomer().getContact(),
                                c.getReceiverName(),
                                c.getReceiverAddress(),
                                c.getDeliveryPerson() != null
                                        ? c.getDeliveryPerson().getFirstName() + " " +
                                          c.getDeliveryPerson().getLastName()
                                        : "Not Assigned",
                                c.getDeliveryPerson() != null
                                        ? c.getDeliveryPerson().getContact()
                                        : "N/A",
                                c.getStatus() != null ? c.getStatus().toString() : "N/A",
                                c.getDeliveryMessage()
                        ))
                        .toList();

        return new ApiResponse<>(true, "Company couriers fetched", response);
    }
}
