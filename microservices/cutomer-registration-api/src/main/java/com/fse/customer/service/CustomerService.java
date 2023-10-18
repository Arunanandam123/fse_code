package com.fse.customer.service;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fse.customer.entity.Customer;
import com.fse.customer.entity.Role;
import com.fse.customer.exception.CustomConstraintException;
import com.fse.customer.model.CustomerRequest;
import com.fse.customer.model.RoleName;
import com.fse.customer.repository.CustomerRepository;
import com.fse.customer.repository.RoleRepository;

@Service
public class CustomerService {
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    @Autowired
    private CustomerRepository customerRepository;
  
    @Autowired
    private RoleRepository roleRepository;
        
    @Autowired
    private PasswordEncoder passwordEncoder;  
    
    public Customer registerCustomer(@Valid CustomerRequest customerRequest) {
    	if (customerRepository.findByEmail(customerRequest.getEmail()).isPresent()) {
    		logger.debug("Email id {} exisits already.",customerRequest.getEmail());
    		throw new CustomConstraintException("EMAIL-ID-EXISTS", "Email already registered");
        }
    	Customer customer = new Customer();
        customer.setName(customerRequest.getName());        
        customer.setEmail(customerRequest.getEmail());
        customer.setPhoneNumber(customerRequest.getPhoneNumber());
        Set<Role> customerRoles = new HashSet<>();
        String hashedPassword = passwordEncoder.encode(customerRequest      		
        		.getPassword());
        customer.setPassword(hashedPassword);
        for (RoleName roleName : customerRequest.getRoleNames()) {
            Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new CustomConstraintException("ROLE-NOT-FOUND", "Invalid Role provided"));
            customerRoles.add(role);
        }

        customer.setRoles(customerRoles);

        return customerRepository.save(customer);
    }
}

