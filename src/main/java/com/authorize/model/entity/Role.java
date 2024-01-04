package com.authorize.model.entity;

import java.util.List;

import com.authorize.model.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private UserRole name;
	
	@ManyToMany(mappedBy = "role")
    List<User> user;
	
	@OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    private List<RoleComponentPrivilege> roleComponentPrivileges;
	
	public Role(UserRole name) {
		this.name = name;
	}
}
