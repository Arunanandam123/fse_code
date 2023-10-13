package com.fse.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fse.auth.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
