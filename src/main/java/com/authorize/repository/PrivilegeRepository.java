package com.authorize.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authorize.model.entity.Privilege;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer>{

	Optional<Privilege> findByName(String name);
}
