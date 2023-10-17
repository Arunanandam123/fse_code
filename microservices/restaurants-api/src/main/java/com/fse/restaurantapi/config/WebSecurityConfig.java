package com.fse.restaurantapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig {

//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable()
//			.addFilterAfter(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class)
//			.authorizeRequests()
//			.anyRequest().authenticated();
//	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) -> authz.antMatchers("/swagger-ui/index.html").permitAll().antMatchers("/food/api/**").authenticated()).csrf(AbstractHttpConfigurer::disable)
				.addFilterAfter(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
