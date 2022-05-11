package com.example.payroll.web.controller;

import com.example.payroll.persistence.model.Role;
import com.example.payroll.service.RoleService;
import com.example.payroll.web.dto.RoleDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleRestController {

    RoleService roleService;

    public RoleRestController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/role/new")
    public Role createRole(@RequestBody RoleDto roleDto) {
        return roleService.newRole(roleDto);
    }
}
