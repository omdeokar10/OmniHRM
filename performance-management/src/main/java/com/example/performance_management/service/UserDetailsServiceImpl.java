package com.example.performance_management.service;

import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.role.Permission;
import com.example.performance_management.entity.role.Role;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.repo.EmployeeRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final EmployeeRepo employeeRepo;

    public UserDetailsServiceImpl(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("user details service.");
        Optional<Employee> user = employeeRepo.findByUserNameStartsWith(username);
        if (user.isEmpty()) {
            throw new CustomException("User not present!");
        }
        Employee verifiedUser = user.get();
        List<Role> roles = verifiedUser.getRoles();

        List<Permission> permissions = new ArrayList<>();
        roles.forEach(role -> permissions.addAll(role.getPermissions()));

        return new org.springframework.security.core.userdetails.User(
                verifiedUser.getUserName(),
                verifiedUser.getPassword(),
                verifiedUser.isEnabled(), true, true, true,
                permissions);
    }





}
