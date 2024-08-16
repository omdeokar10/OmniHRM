package com.example.performance_management.controller;

import com.example.performance_management.dto.RolePermissionDto;
import com.example.performance_management.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        roleService.addRole(rolePermissionDto.getRole());
        return ResponseEntity.ok("Role added:" + rolePermissionDto.getRole());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> addRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok("Role deleted.");
    }

}
