package com.example.performance_management.service;

import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.role.Role;
import com.example.performance_management.entity.role.RoleEnum;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.RoleRepo;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Getter
public class RoleService {

    final private RoleRepo roleRepo;
    final private EmployeeSequenceGeneratorService employeeSequenceGeneratorService;

    @Autowired
    public RoleService(RoleRepo roleRepo, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.roleRepo = roleRepo;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }

    public void addRole(String roleName) {
        Role role = new Role(getRoleId(), roleName);
        roleRepo.save(role);
    }

    public void addRoleForEmployee(String role, Employee employee) {
        Role roleFromRepo = getRole(role);
        if (employee.getRoles() == null) {
            employee.setRoles(List.of(roleFromRepo));
        } else if (!employee.getRoles().contains(roleFromRepo)) {
            employee.getRoles().add(roleFromRepo);
        } else {
            System.out.println("Role already assigned to user.");
        }
    }

    public void deleteRoleForEmployee(String role, Employee employee) {
        Role roleFromRepo = getRole(role);
        if (employee.getRoles() != null) {
            employee.getRoles().remove(roleFromRepo);
        }
    }

    public Role getRole(RoleEnum roleEnum) {
        return getRole(roleEnum.getRoleName());
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

    public void deleteRole(Long id) {
        roleRepo.deleteById(id);
    }

    public boolean isAllowed(List<Role> userRoles, List<Role> allowedRoles) {

        for (Role role : allowedRoles) {
            if (userRoles.contains(role)) {
                return true;
            }
        }
        return false;
    }
}
