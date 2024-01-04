package com.authorize.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
public class Privilege {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	
	@OneToMany(mappedBy = "privilege")
    private List<RoleComponentPrivilege> roleComponentPrivileges;
	
	
	public Privilege(String name) {
		this.name = name;
	}

}
