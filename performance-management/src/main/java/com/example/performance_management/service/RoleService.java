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
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Getter
public class RoleService {

    final private RoleRepo roleRepo;
    private final EmployeeService employeeService;
    private final PermissionService permissionService;
    private final AuthService authService;
    final private EmployeeSequenceGeneratorService employeeSequenceGeneratorService;

    @Autowired
    public RoleService(RoleRepo roleRepo, EmployeeService employeeService, PermissionService permissionService, AuthService authService, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.roleRepo = roleRepo;
        this.employeeService = employeeService;
        this.permissionService = permissionService;
        this.authService = authService;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }

    public Collection<? extends GrantedAuthority> getRolesForCurrentUser() {
        String username = authService.getNameForCurrentUser();
        UserDetails userDetails = authService.getCurrentUserDetails(username);
        Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
        return authorities;
    }


    public void addRole(String roleName, String permStr) {
        Role role = new Role(getRoleId(), roleName, permissionService.deserializePermissions(permStr));
        roleRepo.save(role);
    }

    public void addRoleForEmployee(String role, String user) {

        Employee employee = employeeService.getEmployeeByName(user);
        Role roleFromRepo = getRoleFromRepo(role);

        if (!employee.getRoles().contains(roleFromRepo)) {
            employee.getRoles().add(roleFromRepo);
            employeeService.updateEmployee(employee.getId(), employee);
        }
        else {
            System.out.println("Role already assigned to user.");
        }
    }

    public boolean deleteRoleForEmployee(String role, String user) {
        Employee employee = employeeService.getEmployeeByName(user);
        Role roleFromRepo = getRoleFromRepo(role);
        boolean result = employee.getRoles().remove(roleFromRepo);
        employeeService.updateEmployee(employee.getId(), employee);
        return result;
    }

    public Role getRoleFromRepo(String role) {

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
