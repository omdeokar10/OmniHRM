package com.example.performance_management.service;

import com.example.performance_management.entity.Employee;
import com.example.performance_management.repo.EmployeeRepo;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
            throw new UsernameNotFoundException("User not present!");
        }
        Employee verifiedUser = user.get();
        List<String> roles = verifiedUser.getRoles();
        List<SimpleGrantedAuthority> collect = roles.stream().map(role -> new SimpleGrantedAuthority(role)).toList();

        return new org.springframework.security.core.userdetails.User(
                verifiedUser.getUserName(),
                verifiedUser.getPassword(),
                verifiedUser.isEnabled(), true, true, true,
                collect);
    }
}
