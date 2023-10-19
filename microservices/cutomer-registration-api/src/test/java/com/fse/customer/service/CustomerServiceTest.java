package com.fse.customer.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fse.customer.entity.Customer;
import com.fse.customer.entity.Role;
import com.fse.customer.exception.CustomConstraintException;
import com.fse.customer.model.CustomerRequest;
import com.fse.customer.model.RoleName;
import com.fse.customer.repository.CustomerRepository;
import com.fse.customer.repository.RoleRepository;

@ExtendWith(SpringExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void testRegisterCustomer() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Test");
        customerRequest.setEmail("a@example.com");
        customerRequest.setPassword("password");
        customerRequest.setPhoneNumber("1234567890");
        Set<RoleName> roleNames = new HashSet<>();
        roleNames.add(RoleName.ROLE_USER);
        customerRequest.setRoleNames(roleNames);

        when(customerRepository.findByEmail("a@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(roleRepository.findByName(RoleName.ROLE_USER)).thenReturn(Optional.of(new Role()));

        Customer registeredCustomer = customerService.registerCustomer(customerRequest);
       //todo
        
    }

    @Test
    public void testRegisterCustomerWithEmailExists() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Test");
        customerRequest.setEmail("a@example.com");

        when(customerRepository.findByEmail("a@example.com")).thenReturn(Optional.of(new Customer()));

        assertThrows(CustomConstraintException.class, () -> customerService.registerCustomer(customerRequest));
    }

    @Test
    public void testRegisterCustomerWithInvalidRole() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Test");
        customerRequest.setEmail("a@example.com");
        customerRequest.setPassword("password");
        customerRequest.setPhoneNumber("1234567890");
        Set<RoleName> roleNames = new HashSet<>();
        roleNames.add(RoleName.ROLE_ADMIN); 
        customerRequest.setRoleNames(roleNames);

        when(customerRepository.findByEmail("a@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(roleRepository.findByName(RoleName.ROLE_ADMIN)).thenReturn(Optional.empty());

        assertThrows(CustomConstraintException.class, () -> customerService.registerCustomer(customerRequest));
    }
}

