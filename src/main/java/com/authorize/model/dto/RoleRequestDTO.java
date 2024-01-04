package com.authorize.model.dto;

import java.util.List;

import com.authorize.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class RoleRequestDTO {
	private int id;
	private UserRole name;
	private List<String> components;
	private List<String> privileges;
}
