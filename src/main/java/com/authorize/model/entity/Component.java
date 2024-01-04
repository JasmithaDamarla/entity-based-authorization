package com.authorize.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Component {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@OneToMany(mappedBy = "component")
	private List<RoleComponentPrivilege> roleComponentPrivileges;
	
	public Component() {}
	public Component(String name) {
		this.name = name;
	}
}
