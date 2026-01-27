package com.couriersphere.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.couriersphere.dto.AdminAddCourierCompanyRequest;
import com.couriersphere.dto.AdminCourierCompanyResponse;
import com.couriersphere.dto.AdminCourierResponse;
import com.couriersphere.dto.AdminCustomerResponse;
import com.couriersphere.dto.AdminDeliveryPersonResponse;
import com.couriersphere.dto.AdminRegisterRequest;
import com.couriersphere.dto.AdminResponse;
import com.couriersphere.dto.ApiResponse;
import com.couriersphere.dto.LoginRequest;
import com.couriersphere.entity.Admin;
import com.couriersphere.entity.Courier;
import com.couriersphere.entity.CourierCompany;
import com.couriersphere.entity.DeliveryPerson;
import com.couriersphere.repository.AdminRepository;
import com.couriersphere.repository.CourierCompanyRepository;
import com.couriersphere.repository.CourierRepository;
import com.couriersphere.repository.CustomerRepository;
import com.couriersphere.repository.DeliveryPersonRepository;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final CourierCompanyRepository courierCompanyRepository;
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final CourierRepository courierRepository;
    private final CustomerRepository customerRepository;

    public AdminServiceImpl(
            AdminRepository adminRepository,
            CourierCompanyRepository courierCompanyRepository,
            DeliveryPersonRepository deliveryPersonRepository,
            CourierRepository courierRepository,
            CustomerRepository customerRepository) {

        this.adminRepository = adminRepository;
        this.courierCompanyRepository = courierCompanyRepository;
        this.deliveryPersonRepository = deliveryPersonRepository;
        this.courierRepository = courierRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public ApiResponse<List<AdminCustomerResponse>> getAllCustomers() {
        List<AdminCustomerResponse> response =
                customerRepository.findAll()
                        .stream()
                        .map(c -> new AdminCustomerResponse(
                                c.getId(),
                                c.getFirstName(),
                                c.getLastName(),
                                c.getEmail(),
                                c.getContact(),
                                c.getAddress(),
                                c.getCustomerRefId()
                        ))
                        .toList();

        return new ApiResponse<>(true, "All customers fetched successfully", response);
    }

    @Override
    public ApiResponse<AdminResponse> registerAdmin(AdminRegisterRequest request) {
        if (adminRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Admin email already exists");
        }

        Admin admin = new Admin();
        admin.setEmail(request.getEmail());
        admin.setPassword(request.getPassword());

        Admin savedAdmin = adminRepository.save(admin);
        AdminResponse response = new AdminResponse(savedAdmin.getId(), savedAdmin.getEmail());

        return new ApiResponse<>(true, "Admin registered successfully", response);
    }

    @Override
    public ApiResponse<AdminResponse> login(LoginRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!admin.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        AdminResponse response = new AdminResponse(admin.getId(), admin.getEmail());
        return new ApiResponse<>(true, "Login successful", response);
    }

    @Override
    public ApiResponse<List<AdminCourierCompanyResponse>> getAllCourierCompanies() {
        List<CourierCompany> companies = courierCompanyRepository.findAll();
        List<AdminCourierCompanyResponse> response =
                companies.stream()
                        .map(c -> new AdminCourierCompanyResponse(
                                c.getId(),
                                c.getCompanyName(),
                                c.getEmail(),
                                c.getContact(),
                                c.getStreet(),
                                c.getLandmark(),
                                c.getCity(),
                                c.getPincode(),
                                c.getState(),
                                c.getCountry()
                        ))
                        .collect(Collectors.toList());

        return new ApiResponse<>(true, "Courier companies fetched successfully", response);
    }

    @Override
    public ApiResponse<List<AdminDeliveryPersonResponse>> getAllDeliveryPersons() {
        List<DeliveryPerson> persons = deliveryPersonRepository.findAll();
        List<AdminDeliveryPersonResponse> response =
                persons.stream()
                        .map(p -> new AdminDeliveryPersonResponse(
                                p.getId(),
                                p.getFirstName(),
                                p.getLastName(),
                                p.getEmail(),
                                p.getContact(),
                                p.isActive(),
                                p.getCourierCompany() != null
                                        ? p.getCourierCompany().getCompanyName()
                                        : "N/A"
                        ))
                        .toList();

        return new ApiResponse<>(true, "Delivery persons fetched successfully", response);
    }

    @Override
    public ApiResponse<List<AdminCourierResponse>> getAllCouriers() {
        List<Courier> couriers = courierRepository.findAll();
        List<AdminCourierResponse> response =
                couriers.stream()
                        .map(c -> new AdminCourierResponse(
                                c.getId(),
                                c.getTrackingNumber(),
                                c.getCourierCompany() != null
                                        ? c.getCourierCompany().getCompanyName()
                                        : "N/A",
                                c.getCustomer() != null
                                        ? c.getCustomer().getFirstName() + " " +
                                          c.getCustomer().getLastName()
                                        : "N/A",
                                c.getCustomer() != null
                                        ? c.getCustomer().getCustomerRefId()
                                        : "N/A",
                                c.getCourierCategory() != null ? c.getCourierCategory().toString() : "N/A",
                                c.getWeight(),
                                c.getReceiverName(),
                                c.getReceiverAddress(),
                                c.getDeliveryPerson() != null
                                        ? c.getDeliveryPerson().getFirstName() + " " +
                                          c.getDeliveryPerson().getLastName()
                                        : "Not Assigned",
                                c.getDeliveryPerson() != null
                                        ? c.getDeliveryPerson().getContact()
                                        : "N/A",
                                c.getStatus() != null ? c.getStatus().toString() : "N/A"
                        ))
                        .toList();

        return new ApiResponse<>(true, "All customer couriers fetched successfully", response);
    }

    @Override
    public ApiResponse<String> addCourierCompany(AdminAddCourierCompanyRequest request) {
        if (courierCompanyRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Courier company email already exists");
        }

        CourierCompany company = new CourierCompany();
        company.setCompanyName(request.getCompanyName());
        company.setEmail(request.getEmail());
        company.setPassword(request.getPassword());
        company.setContact(request.getContact());
        company.setStreet(request.getStreet());
        company.setLandmark(request.getLandmark());
        company.setCity(request.getCity());
        company.setPincode(request.getPincode());
        company.setState(request.getState());
        company.setCountry(request.getCountry());

        courierCompanyRepository.save(company);
        return new ApiResponse<>(true, "Courier company added successfully", null);
    }

    @Override
    public ApiResponse<String> deleteCourierCompany(Long companyId) {
        CourierCompany company = courierCompanyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Courier company not found"));

        boolean hasCouriers = courierRepository.existsByCourierCompanyId(companyId);
        if (hasCouriers) {
            throw new RuntimeException("Cannot delete courier company with existing couriers");
        }

        courierCompanyRepository.delete(company);
        return new ApiResponse<>(true, "Courier company deleted successfully", null);
    }
}
