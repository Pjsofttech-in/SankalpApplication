package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.Role;
import com.sankalpapp.repository.RoleRepository;
import com.sankalpapp.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role saveRole(Role role) {

        return roleRepository.save(role);
    }

    @Override
    public Role updateRole(Long id, Role role) {

        Role existingRole = getRoleById(id);

        existingRole.setRoleName(role.getRoleName());
        existingRole.setDescription(role.getDescription());

        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteRole(Long id) {

        roleRepository.deleteById(id);
    }

    @Override
    public Role getRoleById(Long id) {

        return roleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role Not Found"));
    }

    @Override
    public List<Role> getAllRoles() {

        return roleRepository.findAll();
    }
}