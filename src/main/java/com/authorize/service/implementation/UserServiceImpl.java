package com.authorize.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authorize.exceptions.OrganizationNotFoundException;
import com.authorize.exceptions.SignUpFailedException;
import com.authorize.exceptions.UnAuthorizedException;
import com.authorize.exceptions.UserNotFoundException;
import com.authorize.model.UserRole;
import com.authorize.model.dto.UserDTO;
import com.authorize.model.entity.Organization;
import com.authorize.model.entity.Role;
import com.authorize.model.entity.User;
import com.authorize.repository.OrganizationRepository;
import com.authorize.repository.RoleRepository;
import com.authorize.repository.UserRepository;
import com.authorize.service.interfaces.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private OrganizationRepository organizationRepository;

	@Override
	public UserDTO signUpUser(UserDTO newUserDto) throws SignUpFailedException {
		log.info("entered service");
		List<Role> roles = new ArrayList<>();
		newUserDto.getRole().forEach(role -> {
			roles.add(Optional.ofNullable(roleRepository.findByName(role).get())
					.orElseThrow(() -> new UnAuthorizedException("Invalid role is provided")));
		});

		log.info(roles.toString());
		Organization orgs = Optional.ofNullable(organizationRepository.findByName(newUserDto.getOrgs()))
				.orElseThrow(() -> new OrganizationNotFoundException("No such org found"));

		if (userRepository.existsByNameAndOrganizationName(newUserDto.getName(), newUserDto.getOrgs())) {
			throw new SignUpFailedException(
					"User with the same name already exists in the organization: " + newUserDto.getName());
		}

		User reportTo = Optional.ofNullable(userRepository.findByName(newUserDto.getReportingTo()).get())
				.orElseThrow(() -> new UserNotFoundException("no such user found"));
		User newUser = userRepository.save(User.builder().role(roles).reportingTo(reportTo).name(newUserDto.getName())
				.password(passwordEncoder.encode(newUserDto.getPassword())).organization(orgs).build());
		log.info("User is created successfully!!");
		return convertToDTO(newUser);
	}

	@Override
	public UserDTO update(UserDTO updateUserDto) throws UserNotFoundException {
		List<Role> roles = new ArrayList<>();
		updateUserDto.getRole().forEach(role -> {
			roles.add(Optional.ofNullable(roleRepository.findByName(role).get())
					.orElseThrow(() -> new UnAuthorizedException("invalid role is provided")));
		});
		log.info("obtained role from db {}", roles.toString());
		Organization orgs = Optional.ofNullable(organizationRepository.findByName(updateUserDto.getOrgs()))
				.orElseThrow(() -> new OrganizationNotFoundException("no such org found"));
		User reportTo = Optional.ofNullable(userRepository.findByName(updateUserDto.getReportingTo()).get())
		.orElseThrow(() -> new UserNotFoundException("no such user found"));
		User updatedUser = userRepository
				.save(User.builder().role(roles).reportingTo(reportTo).name(updateUserDto.getName())
						.password(updateUserDto.getPassword()).id(updateUserDto.getId()).organization(orgs).build());

		log.info("User is updated successfully!!");
		return convertToDTO(updatedUser);
	}

	@Override
	public UserDTO viewUserByUserName(String userName) throws UserNotFoundException {
		User user = Optional.ofNullable(userRepository.findByName(userName).get())
				.orElseThrow(() -> new UserNotFoundException("No user found of username = " + userName));
		log.info("user getting viewd by username {}", userName);
		return convertToDTO(user);
	}

	@Override
	public List<UserDTO> getUsers() {
		log.info("Fetching all users");
		return userRepository.findAll().stream().map(this::convertToDTO).toList();
	}

	@Override
	public void deleteUserByName(String userName) {
		userRepository.deleteByName(userName);
		log.info("user deleted successfully!");
	}

	private UserDTO convertToDTO(User user) {
		log.info("user entity converted into userDTO");
		return UserDTO.builder().id(user.getId()).name(user.getName()).orgs(user.getOrganization().getName())
				.reportingTo(user.getReportingTo().getName()).role(user.getRole().stream().map(Role::getName).toList())
				.build();
	}
}