package com.authorize.service.interfaces;

import com.authorize.model.dto.RoleDTO;

public interface RoleService {

	RoleDTO getRoleByName(String roleName);
	void createRoleComponentPrivilege(String roleName, String componentName, String privilegeName);
	void updateRoleComponentPrivilege(RoleDTO roleDTO);
	void deleteRoleComponentPrivilege(long id);
	void deleteRoleComponentPrivilegeByName(RoleDTO roleDTO);
}
