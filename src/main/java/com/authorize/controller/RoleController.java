package com.authorize.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.authorize.model.dto.RoleDTO;
import com.authorize.service.interfaces.RoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Hidden
@PreAuthorize("hasRole('PROJECT_ADMIN')")
@RequestMapping("/api/roles")
@RestController
public class RoleController {

	@Autowired
	private RoleService roleService;

	@GetMapping
	public ResponseEntity<RoleDTO> getRoleComponentPrivilegesByName(@RequestParam String roleName) {
		log.info("entered role controller to get roles-component-privileges by name");
		return new ResponseEntity<>(roleService.getRoleByName(roleName), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> createRoleComponentPrivileges(@RequestParam String roleName,
			@RequestParam String componentName, @RequestParam String privilegeName) {
		roleService.createRoleComponentPrivilege(roleName, componentName, privilegeName);
		return new ResponseEntity<>("Created role-component-privilege successfully!", HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<String> updateRoleComponentPrivileges(@RequestBody RoleDTO roleDTO) {
		roleService.updateRoleComponentPrivilege(roleDTO);
		return new ResponseEntity<>("Updated Successfullyy!", HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<Void> deleteRoleComponentPrivilegesByName(@RequestBody RoleDTO roleDTO){
		roleService.deleteRoleComponentPrivilegeByName(roleDTO);
		log.info("deleted successfully!");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRoleComponentPrivileges(@PathVariable long id){
		roleService.deleteRoleComponentPrivilege(id);
		log.info("deleted successfully!");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
