package com.fse.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fse.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}
