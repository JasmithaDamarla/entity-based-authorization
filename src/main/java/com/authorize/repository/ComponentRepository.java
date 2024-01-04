package com.authorize.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authorize.model.entity.Component;

public interface ComponentRepository extends JpaRepository<Component, Integer> {

	Optional<Component> findByName(String name);

}
