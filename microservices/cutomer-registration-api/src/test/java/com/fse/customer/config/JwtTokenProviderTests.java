package com.fse.customer.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtTokenProviderTests {
	@Mock
	private UserDetails userDetails;

	private JwtTokenProvider jwtTokenProvider;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		jwtTokenProvider = new JwtTokenProvider();
		ReflectionTestUtils.setField(jwtTokenProvider, "secretKey", "it's a security key");
	}

	@Test
    public void testCreateToken() {        
	  
        when(userDetails.getUsername()).thenReturn("testUser");     
        String token = jwtTokenProvider.createToken(userDetails);
        assertEquals(200, token.length()); 
    }
}
