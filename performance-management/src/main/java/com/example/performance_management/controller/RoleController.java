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

    @GetMapping("")
    public ResponseEntity<String> getRolesForUser() {
        Collection<? extends GrantedAuthority> rolesForCurrentUser = roleService.getRolesForCurrentUser();
        return ResponseEntity.ok(rolesForCurrentUser.toString());
    }

    @PostMapping("")
    public ResponseEntity<String> assignRolesToUser(@RequestBody UserRoleDto userRoleDto) {
        roleService.addRoleForEmployee(userRoleDto.getRole(), userRoleDto.getUser());
        return ResponseEntity.status(HttpStatus.OK).body("Role: " + userRoleDto.getRole() + " added for user: " + userRoleDto.getUser());
    }

    @DeleteMapping("")
    public ResponseEntity<String> deleteRoleForUser(@RequestBody UserRoleDto userRoleDto) {
        boolean result = roleService.deleteRoleForEmployee(userRoleDto.getRole(), userRoleDto.getUser());
        if (!result) {
            System.out.println("Role was not assigned to user");
        }
        return ResponseEntity.ok("OK");
    }

}
