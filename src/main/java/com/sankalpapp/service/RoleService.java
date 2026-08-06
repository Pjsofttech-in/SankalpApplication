package com.sankalpapp.service;

import com.sankalpapp.entity.Role;

import java.util.List;

public interface RoleService {

    Role saveRole(Role role);

    Role updateRole(Long id, Role role);

    void deleteRole(Long id);

    Role getRoleById(Long id);

    List<Role> getAllRoles();
}