package com.fse.restaurantapi.config;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Claims;

class JwtRequestFilterTest {

	private JwtRequestFilter jwtRequestFilter;
	private FilterChain filterChain;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Claims claims;

	@BeforeEach
	void setUp() {
		jwtRequestFilter = new JwtRequestFilter();
		filterChain = mock(FilterChain.class);
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		claims = mock(Claims.class);

		when(request.getHeader("Authorization")).thenReturn(
				"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJmc2UxMjM0IiwiaWF0IjoxNjk3NzE1ODgyLCJleHAiOjE2OTc3MTY0ODIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdfQ.0KxI8Ri3bEOgwKU68Vx8VF7PXJ2GbLP6ASxA4TKXRm9kyKI7A6alUFcYiCiRup6LI-aK1BYIRHK8VQ9lMj68PA");
	}

//	@Test
//    void testDoFilterInternal_ValidToken() throws IOException, ServletException {
//        when(claims.get("authorities")).thenReturn(createAuthorities());
//        when(jwtRequestFilter.validateToken(request)).thenReturn(claims);
//
//        jwtRequestFilter.doFilterInternal(request, response, filterChain);
//
//        verify(filterChain, times(1)).doFilter(request, response);
//    }

	@Test
	void testDoFilterInternal_InvalidToken() throws IOException, ServletException {

		jwtRequestFilter.doFilterInternal(request, response, filterChain);

		verify(response, times(1)).setStatus(HttpServletResponse.SC_FORBIDDEN);

	}

	@Test
    void testDoFilterInternal_InvalidJwtException() throws IOException, ServletException {
        when(claims.get("authorities")).thenReturn(createAuthorities());

        jwtRequestFilter.doFilterInternal(request, response, filterChain);

        verify(response, times(1)).setStatus(HttpServletResponse.SC_FORBIDDEN);
      //  verify(response, times(1)).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

	private List<String> createAuthorities() {
		List<String> authorities = new ArrayList<>();
		authorities.add("ROLE_USER");
		return authorities;
	}
}
