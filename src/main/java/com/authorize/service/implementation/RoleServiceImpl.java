package com.authorize.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.authorize.exceptions.RoleComponentPrivilegeException;
import com.authorize.model.UserRole;
import com.authorize.model.dto.RoleDTO;
import com.authorize.model.entity.Component;
import com.authorize.model.entity.Privilege;
import com.authorize.model.entity.Role;
import com.authorize.model.entity.RoleComponentPrivilege;
import com.authorize.repository.ComponentRepository;
import com.authorize.repository.PrivilegeRepository;
import com.authorize.repository.RoleComponentPrivilegeRepository;
import com.authorize.repository.RoleRepository;
import com.authorize.service.interfaces.RoleService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private ComponentRepository componentRepository;
	@Autowired
	private PrivilegeRepository privilegeRepository;
	@Autowired
	private RoleComponentPrivilegeRepository roleComponentPrivilegeRepository;

	@Override
	public RoleDTO getRoleByName(String roleName) {
		Role role = Optional.ofNullable(roleRepository.findByName(UserRole.valueOf(roleName)).get())
				.orElseThrow(() -> new RoleComponentPrivilegeException("role not found"));
		log.info("obtained role by name from db");
		List<String> componentsPrivileges = role.getRoleComponentPrivileges().stream()
				.map(rcp -> rcp.getComponent().getName() + "_" + rcp.getPrivilege().getName()).toList();
		log.info("{}", componentsPrivileges);
		return RoleDTO.builder().id((long) role.getId()).name(role.getName()).componentsPrivileges(componentsPrivileges)
				.build();
	}

	@Override
	public void createRoleComponentPrivilege(String roleName, String componentName, String privilegeName) {
		Role role = createRoleIfNotFound(UserRole.valueOf(roleName));
		Component component = createComponentIfNotFound(componentName);
		Privilege privilege = createPrivilegeIfNotFound(privilegeName);
//		roleComponentPrivilegeRepository.save(new RoleComponentPrivilege(role, component, privilege));
		createRoleComponentPrivilegeIfNotFound(role,component,privilege);
	}

	@Override
	public void updateRoleComponentPrivilege(RoleDTO roleDTO) {
		RoleComponentPrivilege roleComponentPrivilege = Optional
				.ofNullable(roleComponentPrivilegeRepository.findById(roleDTO.getId()).get())
				.orElseThrow(() -> new RoleComponentPrivilegeException("entered Id not found"));
		log.info("obtained role-component-privilege");
		Role role = Optional.ofNullable(roleRepository.findByName(roleDTO.getName()).get())
				.orElseThrow(() -> new RoleComponentPrivilegeException("role not found"));
		log.info("obtained role");
		for (String componentPrivilege : roleDTO.getComponentsPrivileges()) {
			int idx = componentPrivilege.indexOf('_');
			String componentName = componentPrivilege.substring(0, idx);
			String privilegeName = componentPrivilege.substring(idx + 1);

			Component component = Optional.ofNullable(componentRepository.findByName(componentName).get())
					.orElseThrow(() -> new RoleComponentPrivilegeException("component not found"));
			log.info("obtained component by name from db");
			Privilege privilege = Optional.ofNullable(privilegeRepository.findByName(privilegeName).get())
					.orElseThrow(() -> new RoleComponentPrivilegeException("privilege not found"));
			log.info("obtained privilege by name from db");
			createRoleComponentPrivilegeIfNotFound(role,component,privilege);
			
		}
	}

	@Override
	public void deleteRoleComponentPrivilege(long id) {
		roleComponentPrivilegeRepository.deleteById(id);
	}
	
	@Transactional
	@Override
	public void deleteRoleComponentPrivilegeByName(RoleDTO roleDTO) {
		RoleComponentPrivilege roleComponentPrivilege = Optional
				.ofNullable(roleComponentPrivilegeRepository.findById(roleDTO.getId()).get())
				.orElseThrow(() -> new RoleComponentPrivilegeException("entered Id not found"));
		log.info("obtained role-component-privilege");
		
		Role role = Optional.ofNullable(roleRepository.findByName(roleDTO.getName()).get())
				.orElseThrow(() -> new RoleComponentPrivilegeException("role not found"));
		log.info("obtained role");
		
		for (String componentPrivilege : roleDTO.getComponentsPrivileges()) {
			int idx = componentPrivilege.indexOf('_');
			String componentName = componentPrivilege.substring(0, idx);
			String privilegeName = componentPrivilege.substring(idx + 1);

			Component component = Optional.ofNullable(componentRepository.findByName(componentName).get())
					.orElseThrow(() -> new RoleComponentPrivilegeException("component not found"));
			log.info("obtained component by name from db");
			Privilege privilege = Optional.ofNullable(privilegeRepository.findByName(privilegeName).get())
					.orElseThrow(() -> new RoleComponentPrivilegeException("privilege not found"));
			log.info("obtained privilege by name from db");
			roleComponentPrivilegeRepository.deleteByRoleAndComponentAndPrivilege(role, component, privilege);
			
		}
//		List<String> componentsPrivileges = role.getRoleComponentPrivileges().stream()
//				.map(rcp -> rcp.getComponent().getName() + "_" + rcp.getPrivilege().getName()).toList();
//		return RoleDTO.builder().id((long) role.getId()).name(role.getName()).componentsPrivileges(componentsPrivileges)
//				.build();
		
	}


	@Transactional
	public void createRoleComponentPrivilegeIfNotFound(Role role, Component component, Privilege privilege) {
		List<RoleComponentPrivilege> roleComponentPri = roleComponentPrivilegeRepository
				.findByRoleAndComponentAndPrivilege(role,component,privilege);
		if (roleComponentPri.isEmpty()) {
			log.info("created role-component-repository");
			roleComponentPrivilegeRepository.save(new RoleComponentPrivilege(role,component,privilege));
		}

	}

	@Transactional
	public Component createComponentIfNotFound(final String name) {
		Optional<Component> component = componentRepository.findByName(name);
		if (component.isEmpty()) {
			component = Optional.of(new Component(name));
			component = Optional.of(componentRepository.save(component.get()));
			log.info("component created");
		}
		return component.get();
	}

	@Transactional
	public Role createRoleIfNotFound(final UserRole name) {
		Optional<Role> role = roleRepository.findByName(name);
		if (role.isEmpty()) {
			role = Optional.of(new Role(name));
			role = Optional.of(roleRepository.save(role.get()));
			log.info("role created");
		}
		return role.get();
	}

	@Transactional
	public Privilege createPrivilegeIfNotFound(final String name) {
		Optional<Privilege> privilege = privilegeRepository.findByName(name);
		if (privilege.isEmpty()) {
			privilege = Optional.of(new Privilege(name));
			privilege = Optional.of(privilegeRepository.save(privilege.get()));
			log.info("privilege created");
		}
		return privilege.get();
	}
}
