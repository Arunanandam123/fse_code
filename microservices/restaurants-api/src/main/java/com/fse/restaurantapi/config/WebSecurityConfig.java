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

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()
				.authorizeHttpRequests((authz) -> authz.antMatchers("/swagger-ui/index.html").permitAll()
						.antMatchers("/food/api/v1/admin/**").permitAll().antMatchers("/food/api/v1/user/search/**")
						.permitAll().antMatchers("/food/api/v1/user/register").permitAll().antMatchers("/food/api/v1/user/login").permitAll())
				.csrf(AbstractHttpConfigurer::disable)
				.addFilterAfter(new JwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
