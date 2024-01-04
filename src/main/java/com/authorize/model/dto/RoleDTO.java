package com.authorize.model.dto;

import java.util.List;

import com.authorize.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
	
	private Long id;
	private UserRole name;
	private List<String> componentsPrivileges;
}
