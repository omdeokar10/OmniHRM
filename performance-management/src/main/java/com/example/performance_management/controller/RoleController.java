package com.example.performance_management.controller;

import com.example.performance_management.dto.RolePermissionDto;
import com.example.performance_management.dto.UserRoleDto;
import com.example.performance_management.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/role")
@CrossOrigin("*")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addRole(@RequestBody RolePermissionDto rolePermissionDto) {
        roleService.addRole(rolePermissionDto.getRole(), rolePermissionDto.getPermissionString());
        return ResponseEntity.ok("Role added:"+rolePermissionDto.getRole());
    }





}
