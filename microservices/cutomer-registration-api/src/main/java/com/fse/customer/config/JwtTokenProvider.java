package com.fse.customer.config;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {

	@Value("${auth.secret.key}")
	private String secretKey;
	
	public String createToken(UserDetails userDetails) {
		Date now = new Date();		
		
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
