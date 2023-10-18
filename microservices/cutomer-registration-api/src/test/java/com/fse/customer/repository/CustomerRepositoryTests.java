package com.fse.customer.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.fse.customer.entity.Customer;

@DataJpaTest
public class CustomerRepositoryTests {
	@Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testSaveUser() {
        // Create a user and save it to the database
    	Customer user = new Customer();
    	user.setName("name");
    	user.setPassword("password");
    	user.setEmail("a@b.com");
    	customerRepository.save(user);

        
        Customer savedUser = customerRepository.findByName("name");

        // Assert that the saved user is not null and has the expected properties
        assertNotNull(savedUser);
    }
}
