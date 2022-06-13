package com.example.payroll.service;

import com.example.payroll.persistence.dao.RoleRepository;
import com.example.payroll.persistence.model.Role;
import com.example.payroll.web.dto.RoleDto;
import com.example.payroll.web.error.RoleAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role newRole(RoleDto roleDto) {
        if (roleExists(roleDto)) {
            throw new RoleAlreadyExistsException("There is already role with name: " + roleDto.getName());
        }
        Role role = new Role();
        role.setName(roleDto.getName());
        return roleRepository.save(role);
    }

    private boolean roleExists(RoleDto roleDto) {
        return roleRepository.findRoleByName(roleDto.getName()).isPresent();
    }
}
