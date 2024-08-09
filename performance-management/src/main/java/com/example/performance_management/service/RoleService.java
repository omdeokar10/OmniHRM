package com.example.performance_management.service;

import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.Role;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.RoleRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
@Transactional
@Getter
public class RoleService {

    final private RoleRepo roleRepo;
    private final PermissionService permissionService;
    final private EmployeeSequenceGeneratorService employeeSequenceGeneratorService;

    @Autowired
    public RoleService(RoleRepo roleRepo, PermissionService permissionService, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.roleRepo = roleRepo;
        this.permissionService = permissionService;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }


    public void addRole(String roleName, String permStr) {
        Role role = new Role(getRoleId(), roleName, permissionService.deserializePermissions(permStr));
        roleRepo.save(role);
    }

    public void addRoleForEmployee(String role, Employee employee) {
        Role roleFromRepo = getRole(role);
        if (!employee.getRoles().contains(roleFromRepo)) {
            employee.getRoles().add(roleFromRepo);
        } else {
            System.out.println("Role already assigned to user.");
        }
    }

    public void deleteRoleForEmployee(String role, Employee employee) {
        Role roleFromRepo = getRole(role);
        employee.getRoles().remove(roleFromRepo);
    }

    public Role getRole(String role) {

        Optional<Role> optionalRole = roleRepo.findByRoleNameStartsWith(role);
        if (optionalRole.isEmpty()) {
            throw new CustomException("Role does not exist");
        } else {
            return optionalRole.get();
        }
    }

    private Long getRoleId() {
        return employeeSequenceGeneratorService.getRoleSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Role.GENERATED_ID);
    }


}
