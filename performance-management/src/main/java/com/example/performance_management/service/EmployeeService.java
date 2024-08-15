package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.role.Role;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.EmployeeMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.EmployeeRepo;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final RoleService roleService;
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Function<Employee, EmployeeDto> mapEmployee = (employee) -> employeeMapper.convertToDto(employee);

    public EmployeeService(EmployeeRepo employeeRepo,
                           RoleService roleService, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.employeeRepo = employeeRepo;
        this.roleService = roleService;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }

    public void signup(EmployeeDto employeeDto) {
        createEmployee(employeeDto);
    }

    public void createEmployee(EmployeeDto employeeDto) {
        performChecks(employeeDto);
        Employee employee = employeeMapper.convertToEntity(employeeDto, passwordEncoder);
        employeeRepo.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll().stream().toList();
    }

    public List<EmployeeDto> getAllEmployeeDto() {
        List<Employee> employees = getAllEmployees();
        return employees.stream().map(employee -> employeeMapper.convertToDto(employee))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(Long id) {
        Supplier<CustomException> supplier = () -> new CustomException("Employee does not exist.");
        return employeeRepo.findById(id).orElseThrow(supplier);
    }


    public Employee getEmployeeByEmail(String email) {
        return employeeRepo.findByEmailStartsWith(email).orElseThrow(() -> new CustomException("Employee does not exist."));
    }

    private Employee getEmployeeByUsername(String username) {
        Supplier<CustomException> supplier = () -> new CustomException("Employee does not exist.");
        return employeeRepo.findByUserNameStartsWith(username).orElseThrow(supplier);
    }

    public EmployeeDto getEmployeeDtoByUsername(String username) {
        Supplier<CustomException> supplier = () -> new CustomException("Employee does not exist.");
        Employee employee = employeeRepo.findByUserNameStartsWith(username).orElseThrow(supplier);
        return employeeMapper.convertToDto(employee);
    }

    public EmployeeDto getEmployeeDtoById(Long id) {
        Employee employee = getEmployeeById(id);
        return mapEmployee.apply(employee);
    }

    private void performChecks(EmployeeDto employeeDto) {
        if (checkIfUserExistsWithName(employeeDto.getUserName()) || checkForEmail(employeeDto.getEmail())) {
            throw new CustomException("Name already exists");
        }
    }

    public boolean checkIfUserExistsWithName(String nameStr) {
        return employeeRepo.findByUserNameStartsWith(nameStr).isPresent();
    }

    private boolean checkForEmail(String email) {
        return employeeRepo.findByEmailStartsWith(email).isPresent();
    }

    public void updateEmployee(Long id, EmployeeDto dto) {
        Employee employee = getEmployee(employeeRepo.findById(id));
        Employee updatedEmployee = employeeMapper.updateEmployee(employee, dto, passwordEncoder);
        employeeRepo.save(updatedEmployee);
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployee(employeeRepo.findById(id));
        employeeRepo.delete(employee);
    }

    public Employee getEmployeeByName(String user) {
        return getEmployee(employeeRepo.findByUserNameStartsWith(user));
    }

    private Employee getEmployee(Optional<Employee> user) {
        if (user.isEmpty()) {
            throw new CustomException("Employee is not present");
        }
        return user.get();
    }

    public List<Role> getRolesForUser(String name) {
        Employee emp = getEmployeeByUsername(name);
        List<Role> roles = emp.getRoles();
        return roles;
    }

    public void addRoleForEmployee(String role, String user) {
        Employee employee = getEmployeeByUsername(user);
        roleService.addRoleForEmployee(role, employee);
        employeeRepo.save(employee);
    }

    public void deleteRoleForEmployee(String role, String user) {
        Employee employee = getEmployeeByUsername(user);
        roleService.deleteRoleForEmployee(role, employee);
        employeeRepo.save(employee);
    }

    public void resetPassword(String username, String password) {
        Employee employee = getEmployeeByUsername(username);
        employee.setPassword(passwordEncoder.encode(password));
    }

    public EmployeeDto getEmployeeDtoByEmail(String email) {
        Employee employee = employeeRepo.findByEmailStartsWith(email).orElseThrow(() -> new CustomException("Employee does not exist."));
        return employeeMapper.convertToDto(employee);
    }
}