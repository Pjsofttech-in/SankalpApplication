package com.sankalpapp.controller;

import com.sankalpapp.entity.Role;
import com.sankalpapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RoleController {

    private final RoleService roleService;

    // Save Role
    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role saveRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    // Get All Roles
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Get Role By Id
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role getRoleById(@PathVariable Long id) {
        return roleService.getRoleById(id);
    }

    // Update Role
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Role updateRole(@PathVariable Long id,
                           @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    // Delete Role
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteRole(@PathVariable Long id) {

        roleService.deleteRole(id);

        return "Role Deleted Successfully";
    }
}