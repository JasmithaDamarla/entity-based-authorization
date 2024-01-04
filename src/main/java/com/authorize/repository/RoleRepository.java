package com.authorize.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorize.model.UserRole;
import com.authorize.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

	Optional<Role> findByName(UserRole name);
}
