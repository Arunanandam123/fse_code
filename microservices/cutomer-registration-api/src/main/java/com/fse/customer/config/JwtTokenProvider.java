package com.fse.customer.config;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
	
	private String secretKey = "secret-key"; // Replace with your actual secret key
	//private long validityInMilliseconds = 3600000; // 1 hour

	public String createToken(UserDetails userDetails) {
		Date now = new Date();
		//Date validity = new Date(now.getTime() + validityInMilliseconds);
		
		
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(now)
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.claim("authorities",userDetails.getAuthorities().stream()
						.map(GrantedAuthority::getAuthority)
						.collect(Collectors.toList()))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
	}
}
