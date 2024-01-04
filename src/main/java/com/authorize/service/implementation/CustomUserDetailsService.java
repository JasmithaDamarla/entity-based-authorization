package com.authorize.service.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.authorize.model.entity.Role;
import com.authorize.model.entity.RoleComponentPrivilege;
import com.authorize.model.entity.User;
import com.authorize.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(final String name) throws UsernameNotFoundException {
		try {
			final User user = Optional.ofNullable(userRepository.findByName(name))
					.orElseThrow(() -> new UsernameNotFoundException("No user found with username: " + name)).get();
			return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true,
					true, true, true, getAuthorities(user.getRole()));
		} catch (final Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
		List<RoleComponentPrivilege> components = getComponents(roles);
		List<String> privileges = getPrivileges(components);
		return getGrantedAuthorities(privileges);
	}

	private List<RoleComponentPrivilege> getComponents(final Collection<Role> roles) {
		final List<RoleComponentPrivilege> components = new ArrayList<>();
		for (final Role role : roles) {
			components.addAll(role.getRoleComponentPrivileges());
		}
		return components;
	}

	private List<String> getPrivileges(final Collection<RoleComponentPrivilege> components) {
		final List<String> privileges = new ArrayList<>();
		components.forEach(component -> privileges
				.add(component.getComponent().getName() + "_" + component.getPrivilege().getName()));
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		for (final String privilege : privileges) {
			log.info("{}", privilege);
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
}