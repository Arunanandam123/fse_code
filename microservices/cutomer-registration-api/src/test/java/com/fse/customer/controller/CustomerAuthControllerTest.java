package com.fse.customer.controller;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.customer.config.JwtTokenProvider;
import com.fse.customer.model.CustomerRequest;
import com.fse.customer.model.LoginRequest;
import com.fse.customer.service.CustomUserDetailsService;
import com.fse.customer.service.CustomerService;

@WebMvcTest(CustomerAuthController.class)
public class CustomerAuthControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;

	@MockBean
	private AuthenticationManager authenticationManager;

	@MockBean
	private CustomUserDetailsService customUserDetailsService;

	@MockBean
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@BeforeEach
	public void setup() {
		// Init MockMvc Object and build
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testRegisterCustomerValid() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setName("Test");
		customerRequest.setEmail("Test@example.com");
		customerRequest.setPassword("password");
		customerRequest.setPhoneNumber("1234567890");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/register").content(asJsonString(customerRequest))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Registration successful."));
	}

	@Test
	public void testRegisterCustomerInvalidPhoneNumber() throws Exception {
		CustomerRequest customerRequest = new CustomerRequest();
		customerRequest.setName("Test");
		customerRequest.setEmail("Test@example.com");
		customerRequest.setPassword("password");
		customerRequest.setPhoneNumber("invalidPhoneNumber");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/register").content(asJsonString(customerRequest))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testLoginValid() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("testUser");
		loginRequest.setPassword("password");

		UserDetails userDetails = createUserDetails("testUser", "password");
		when(customUserDetailsService.loadUserByUsername("testUser")).thenReturn(userDetails);
		when(jwtTokenProvider.createToken(userDetails)).thenReturn("testToken");

		mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/login").content(asJsonString(loginRequest))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("testToken"));
	}

	@Test
	public void testLoginInvalid() throws Exception {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("nonExistentUser");
		loginRequest.setPassword("password");

		when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
				.thenThrow(new BadCredentialsException("Bad credentials"));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/customers/login").content(asJsonString(loginRequest))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	}

	private UserDetails createUserDetails(String username, String password) {
		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new org.springframework.security.core.userdetails.User(username, password, authorities);
	}

	private String asJsonString(Object object) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(object);
	}
}
