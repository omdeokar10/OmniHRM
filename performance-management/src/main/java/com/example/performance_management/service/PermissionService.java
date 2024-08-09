package com.example.performance_management.service;

import com.example.performance_management.entity.Permission;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {


    public PermissionService() {
    }

    public List<Permission> deserializePermissions(String permissionString){
        return Arrays.stream(permissionString.split(",")).map(Permission::new).collect(Collectors.toList());
    }

    public String getPermissionFromRole(String role) {
        //todo: implement.
        return "";
    }

    public String getPermissionFromEmployee(String nameForCurrentUser){
//        Employee emp = employeeService.findEmployee(nameForCurrentUser);
//        List<String> roles = emp.getRoles();
//        String permissions = String.join(",", roles);
//        System.out.println("All permissions for user:"+permissions);
//        return permissions;
        return "";
    }

}
