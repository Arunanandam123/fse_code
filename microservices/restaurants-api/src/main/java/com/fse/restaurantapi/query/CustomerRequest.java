package com.fse.restaurantapi.query;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerRequest {
	@NotBlank(message = "Name is required.")
    private String name;

    @Email(message = "Invalid email address.")
    @NotBlank(message = "Email is required.")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;
    
    @Size(min = 10, message = "Phone number must be at least 10 characters long.")
    private String phoneNumber;
    
    private Set<RoleName> roleNames;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<RoleName> getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(Set<RoleName> roleNames) {
		this.roleNames = roleNames;
	}

    
}

