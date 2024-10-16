package com.christoffer.apiimages.repository;

import com.christoffer.apiimages.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}