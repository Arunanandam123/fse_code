package com.fse.customer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fse.customer.entity.Customer;
import com.fse.customer.entity.Role;
import com.fse.customer.model.RoleName;
import com.fse.customer.repository.CustomerRepository;

@ExtendWith(SpringExtension.class)
public class CustomUserDetailsServiceTest {

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private CustomerRepository userRepository;


    @Test
    public void testLoadUserByUsername() {
        String username = "testUser";
        Customer user = createUser(username);
        when(userRepository.findByName(username)).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        assertEquals(username, userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        
    }

    private Customer createUser(String username) {
        Customer customer = new Customer();
        customer.setName(username);
        customer.setPassword("password"); // Set a password
        Role role = new Role();
        role.setName(RoleName.ROLE_USER);
        customer.setRoles(Collections.singleton(role));
        return customer;
    }
}

