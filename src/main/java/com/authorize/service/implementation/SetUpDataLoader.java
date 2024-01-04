//package com.authorize.service.implementation;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.authorize.model.UserRole;
//import com.authorize.model.entity.Component;
//import com.authorize.model.entity.Privilege;
//import com.authorize.model.entity.Role;
//import com.authorize.model.entity.RoleComponentPrivilege;
//import com.authorize.model.entity.User;
//import com.authorize.repository.ComponentRepository;
//import com.authorize.repository.PrivilegeRepository;
//import com.authorize.repository.RoleComponentPrivilegeRepository;
//import com.authorize.repository.RoleRepository;
//import com.authorize.repository.UserRepository;
//
//import jakarta.transaction.Transactional;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@org.springframework.stereotype.Component
//public class SetUpDataLoader implements ApplicationListener<ContextRefreshedEvent> {
//    private boolean alreadySetup = false;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private PrivilegeRepository privilegeRepository;
//
//    @Autowired
//    private ComponentRepository componentRepository;
//
//    @Autowired
//    private RoleComponentPrivilegeRepository roleComponentPrivilegeRepository;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public void onApplicationEvent(final ContextRefreshedEvent event) {
////        if (alreadySetup) {
////            return;
////        }
//
//        // create initial privileges
//        final Privilege createPrivilege = createPrivilegeIfNotFound("CREATE_PRIVILEGE");
//        final Privilege updatePrivilege = createPrivilegeIfNotFound("UPDATE_PRIVILEGE");
//        final Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
//        final Privilege deletePrivilege = createPrivilegeIfNotFound("DELETE_PRIVILEGE");
//
//
//        final Component orgComponent = createComponentIfNotFound("ORGANIZATION");
//        final Component fieldComponent = createComponentIfNotFound("FIELD");
//        final Component taskComponent = createComponentIfNotFound("TASK");
//
//        final Role adminRole = createRoleIfNotFound(UserRole.ROLE_PROJECT_ADMIN);
//        final Role supporterRole = createRoleIfNotFound(UserRole.valueOf("ROLE_FIELD_SUPPORTER"));
//        final Role managerRole = createRoleIfNotFound(UserRole.ROLE_FIELD_MANAGER);
//
//
//        final RoleComponentPrivilege orgAdminCreate = new RoleComponentPrivilege(adminRole, orgComponent, createPrivilege);
//        final RoleComponentPrivilege orgAdminUpdate = new RoleComponentPrivilege(adminRole, orgComponent, updatePrivilege);
//        final RoleComponentPrivilege orgAdminDelete = new RoleComponentPrivilege(adminRole, orgComponent, deletePrivilege);
//        final RoleComponentPrivilege orgAdminRead = new RoleComponentPrivilege(adminRole, orgComponent, readPrivilege);
//
//        final RoleComponentPrivilege fieldAdminCreate = new RoleComponentPrivilege(adminRole, fieldComponent, createPrivilege);
//        final RoleComponentPrivilege fieldAdminUpdate = new RoleComponentPrivilege(adminRole, fieldComponent, updatePrivilege);
//        final RoleComponentPrivilege fieldAdminDelete = new RoleComponentPrivilege(adminRole, fieldComponent, deletePrivilege);
//        final RoleComponentPrivilege fieldAdminRead = new RoleComponentPrivilege(adminRole, fieldComponent, readPrivilege);
//
//        final RoleComponentPrivilege taskAdminCreate = new RoleComponentPrivilege(adminRole, taskComponent, createPrivilege);
//        final RoleComponentPrivilege taskAdminUpdate = new RoleComponentPrivilege(adminRole, taskComponent, updatePrivilege);
//        final RoleComponentPrivilege taskAdminDelete = new RoleComponentPrivilege(adminRole, taskComponent, deletePrivilege);
//        final RoleComponentPrivilege taskAdminRead = new RoleComponentPrivilege(adminRole, taskComponent, readPrivilege);
//
//
//        final RoleComponentPrivilege orgSupporterCreate = new RoleComponentPrivilege(supporterRole, orgComponent, createPrivilege);
//        final RoleComponentPrivilege orgSupporterUpdate = new RoleComponentPrivilege(supporterRole, orgComponent, updatePrivilege);
////        final RoleComponentPrivilege orgSupporterRead = new RoleComponentPrivilege(supporterRole, orgComponent, readPrivilege);
//
//        final RoleComponentPrivilege fieldSupporterCreate = new RoleComponentPrivilege(supporterRole, fieldComponent, createPrivilege);
//        final RoleComponentPrivilege fieldSupporterUpdate = new RoleComponentPrivilege(supporterRole, fieldComponent, updatePrivilege);
//        final RoleComponentPrivilege fieldSupporterRead = new RoleComponentPrivilege(supporterRole, fieldComponent, readPrivilege);
//
//
//        final RoleComponentPrivilege orgManagerRead = new RoleComponentPrivilege(managerRole, orgComponent, readPrivilege);
//        final RoleComponentPrivilege fieldManagerRead = new RoleComponentPrivilege(managerRole, fieldComponent, readPrivilege);
//        final RoleComponentPrivilege taskManagerRead = new RoleComponentPrivilege(managerRole, taskComponent, readPrivilege);
//
//        createRoleComponentPrivilegeIfNotFound(orgAdminCreate, orgAdminUpdate, orgAdminDelete, orgAdminRead,
//                fieldAdminCreate, fieldAdminUpdate, fieldAdminDelete, fieldAdminRead,
//                taskAdminCreate, taskAdminUpdate, taskAdminDelete, taskAdminRead);
//
//        createRoleComponentPrivilegeIfNotFound(orgSupporterCreate, orgSupporterUpdate,
//                fieldSupporterCreate, fieldSupporterUpdate, fieldSupporterRead);
//
//        createRoleComponentPrivilegeIfNotFound(orgManagerRead, fieldManagerRead, taskManagerRead);
//
//        // create initial user
//        createUserIfNotFound("admin@test.com", "admin", "test", "none",List.of(adminRole));
//        createUserIfNotFound("supporter1@test.com", "supporter1", "test", "admin@test.com", List.of(supporterRole));
//        createUserIfNotFound("supporter2@test.com", "supporter2", "test", "admin@test.com", List.of(supporterRole));
//        createUserIfNotFound("manager1@test.com", "manager1", "test", "supporter1@test.com", List.of(managerRole));
//        createUserIfNotFound("manager2@test.com", "manager2", "test", "supporter1@test.com", List.of(managerRole));
//        createUserIfNotFound("manager3@test.com", "manager3",  "test", "supporter2@test.com", List.of(managerRole));
//
////        alreadySetup = true;
//    }
//
//    
//    @Transactional
//    public Privilege createPrivilegeIfNotFound(final String name) {
//        Optional<Privilege> privilege = privilegeRepository.findByName(name);
//        if (privilege.isEmpty()) {
//            privilege = Optional.of(new Privilege(name));
//            privilege = Optional.of(privilegeRepository.save(privilege.get()));
//        }
//        return privilege.get();
//    }
//
//
//    @Transactional
//    public Component createComponentIfNotFound(final String name) {
//        Optional<Component> component = componentRepository.findByName(name);
//        if (component.isEmpty()) {
//            component = Optional.of(new Component(name));
//            component = Optional.of(componentRepository.save(component.get()));
//        }
//        return component.get();
//    }
//
//    @Transactional
//    public Role createRoleIfNotFound(final UserRole name) {
//        Optional<Role> role = roleRepository.findByName(name);
//        if (role.isEmpty()) {
//            role = Optional.of(new Role(name));
//            role = Optional.of(roleRepository.save(role.get()));
//        }
//        return role.get();
//    }
//
//    @Transactional
//    public User createUserIfNotFound(final String email, final String name, final String password, final String reportingTo, final List<Role> roles) {
//        Optional<User> newUser = userRepository.findByName(email);
//        if(!newUser.isPresent()) {
//            User user = new User();
//            user.setName(name);
//            user.setPassword(passwordEncoder.encode(password));
//            user.setEmail(email);
//            user.setRole(roles);
//            user.setReportingTo(findParentAccount(reportingTo));
//            return userRepository.save(user);
//        }
//        return newUser.get();
//    }
//
////    private User findParentAccount(final String email) {
////        return userRepository.findByEmail(email).get();
////    }
//    
////    @Transactional
////    public Privilege createPrivilegeIfNotFound(final String name) {
////        Privilege privilege = privilegeRepository.findByName(name);
////        if (privilege == null) {
////            privilege = new Privilege(name);
////            privilege = privilegeRepository.save(privilege);
////        }
////        return privilege;
////    }
////
////    @Transactional
////    public Component createComponentIfNotFound(final String name) {
////        Component component = componentRepository.findByName(name);
////        if (component == null) {
////            component = new Component(name);
////            component = componentRepository.save(component);
////        }
////        return component;
////    }
////
////    @Transactional
////    public Role createRoleIfNotFound(final String name) {
////        Role role = roleRepository.findByName(name);
////        if (role == null) {
////            role = new Role(name);
////            role = roleRepository.save(role);
////        }
////        return role;
////    }
//
//
//    @Transactional
//    public void createRoleComponentPrivilegeIfNotFound(RoleComponentPrivilege... roleComponentPrivileges) {
//
//        Arrays.stream(roleComponentPrivileges).forEach(roleComponentPrivilege -> {
//            List<RoleComponentPrivilege> roleComponentPri = roleComponentPrivilegeRepository.findByRoleAndComponentAndPrivilege(roleComponentPrivilege.getRole(),
//                    roleComponentPrivilege.getComponent(), roleComponentPrivilege.getPrivilege());
//            if (roleComponentPri.isEmpty()) {
//                roleComponentPrivilegeRepository.save(roleComponentPrivilege);
//            }
//        });
//    }
//
////    @Transactional
////    public User createUserIfNotFound(final String email, final String name, final String password, final String reportingTo, final List<Role> roles) {
////        User user = userRepository.findByEmail(email);
////        if (user == null) {
////            user = new User();
////            user.setName(name);
////            user.setPassword(passwordEncoder.encode(password));
////            user.setEmail(email);
////            user.setRole(roles);
////            user.setReportingTo(findParentAccount(reportingTo));
////            user = userRepository.save(user);
////        }
////        return user;
////    }
////
//    private User findParentAccount(final String email) {
//        Optional<User> parentUser = userRepository.findByEmail(email);
//        if (!parentUser.isEmpty()) {
//            return parentUser.get();
//        }
//        return null;
//    }
//
//}
