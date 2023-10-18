package com.fse.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fse.customer.entity.Customer;
import com.fse.customer.entity.Role;
import com.fse.customer.repository.CustomerRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private CustomerRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Customer userDetails = userRepository.findByName(username);
		if (userDetails == null) {
			logger.debug("User name {} not found",username);
			throw new UsernameNotFoundException("User not found.");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role r : userDetails.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(r.getName().name()));
		}
		return new org.springframework.security.core.userdetails.User(userDetails.getName(), userDetails.getPassword(),
				authorities);
	}
}
