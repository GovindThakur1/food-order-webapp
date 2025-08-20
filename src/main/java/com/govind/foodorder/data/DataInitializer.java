package com.govind.foodorder.data;

import com.govind.foodorder.enums.AdminLevel;
import com.govind.foodorder.model.Admin;
import com.govind.foodorder.model.Customer;
import com.govind.foodorder.model.Role;
import com.govind.foodorder.model.User;
import com.govind.foodorder.repository.AdminRepository;
import com.govind.foodorder.repository.CustomerRepository;
import com.govind.foodorder.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Transactional
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {


    private final RoleRepository roleRepository;
    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Set<String> defaultRoles = Set.of("ROLE_ADMIN", "ROLE_CUSTOMER");
        createDefaultRolesIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
        createDefaultCustomersIfNotExists();
    }

    private void createDefaultRolesIfNotExists(Set<String> defaultRoles) {
        defaultRoles.stream()
                .filter(role -> roleRepository.findByName(role).isEmpty())
                .map(Role::new)
                .forEach(roleRepository::save);
    }

    private void createDefaultAdminIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseThrow(() -> new RuntimeException("ROLE_ADMIN not found"));

        for (int i = 1; i <= 2; i++) {
            String defaultEmail = "admin" + i + "@gmail.com";
            if (adminRepository.existsByEmail(defaultEmail))
                continue;

            Admin admin = Admin.builder()
                    .firstName("Admin" + i)
                    .lastName("admin")
                    .email(defaultEmail)
                    .phone("1234567890")
                    .adminLevel(AdminLevel.STAFF)
                    .password("1234")
                    .role(adminRole)
                    .build();
            adminRepository.save(admin);
        }
    }


    private void createDefaultCustomersIfNotExists() {
        Role adminRole = roleRepository.findByName("ROLE_CUSTOMER")
                .orElseThrow(() -> new RuntimeException("ROLE_CUSTOMER not found"));

        for (int i = 3; i <= 4; i++) {
            String defaultEmail = "customer" + i + "@gmail.com";
            if (customerRepository.existsByEmail(defaultEmail))
                continue;

            Customer customer = Customer.builder()
                    .firstName("Customer" + (i - 2))
                    .lastName("Customer")
                    .email(defaultEmail)
                    .phone("1234567890")
                    .address("Ktm")
                    .role(adminRole)
                    .password("1234")
                    .build();
            customerRepository.save(customer);
        }
    }


}
