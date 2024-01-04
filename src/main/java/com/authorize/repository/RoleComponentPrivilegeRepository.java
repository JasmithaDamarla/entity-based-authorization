package com.authorize.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.authorize.model.entity.Component;
import com.authorize.model.entity.Privilege;
import com.authorize.model.entity.Role;
import com.authorize.model.entity.RoleComponentPrivilege;

public interface RoleComponentPrivilegeRepository extends JpaRepository<RoleComponentPrivilege, Long> {

    @Override
    void delete(RoleComponentPrivilege privilege);
    
    @Query("SELECT rcp FROM RoleComponentPrivilege rcp " +
            "WHERE rcp.role = :role AND rcp.component = :component AND rcp.privilege = :privilege")
    List<RoleComponentPrivilege> findByRoleAndComponentAndPrivilege(
             @Param("role") Role role,
             @Param("component") Component component,
             @Param("privilege") Privilege privilege);
//    List<RoleComponentPrivilege> findByRoleAndComponentAndPrivilege(Role role, Component component, Privilege privilege);
    
    void deleteByRoleAndComponentAndPrivilege(Role role, Component component, Privilege privilege);
}
