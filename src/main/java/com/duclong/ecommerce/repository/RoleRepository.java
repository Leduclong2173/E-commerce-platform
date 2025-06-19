package com.duclong.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duclong.ecommerce.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Role findByName(String name);
}
