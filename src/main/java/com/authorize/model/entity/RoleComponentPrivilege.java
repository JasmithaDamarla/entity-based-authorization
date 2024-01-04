package com.authorize.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "role_component_privilege", uniqueConstraints = {
		@UniqueConstraint(name = "unique_role_component_privilege", columnNames = { "role_id", "component_id",
				"privilege_id" }) })
@NoArgsConstructor
@Entity
public class RoleComponentPrivilege {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@ManyToOne
	@JoinColumn(name = "component_id", nullable = false)
	private Component component;

	@ManyToOne
	@JoinColumn(name = "privilege_id", nullable = false)
	private Privilege privilege;

	public RoleComponentPrivilege(Role role, Component orgComponent, Privilege privilege) {
		this.role = role;
		this.component = orgComponent;
		this.privilege = privilege;
	}
}