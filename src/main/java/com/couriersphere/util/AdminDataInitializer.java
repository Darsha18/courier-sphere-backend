package com.couriersphere.util;

import com.couriersphere.entity.Admin;
import com.couriersphere.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class AdminDataInitializer {

    private final AdminRepository adminRepository;

    @Value("${default.admin.email}")
    private String email;

    @Value("${default.admin.password}")
    private String password;

    public AdminDataInitializer(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @PostConstruct
    public void createDefaultAdmin() {

        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setEmail(email);
            admin.setPassword(password);
            adminRepository.save(admin);
        }
    }
}
