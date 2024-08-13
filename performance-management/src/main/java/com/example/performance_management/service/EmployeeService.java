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
    private final Function<Employee, EmployeeDto> mapEmployee = (employee) -> employeeMapper.convertToDto(employee, passwordEncoder);

    public EmployeeService(EmployeeRepo employeeRepo,
                           RoleService roleService, EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.employeeRepo = employeeRepo;
        this.roleService = roleService;
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll().stream().collect(Collectors.toList());
    }

    public List<EmployeeDto> getAllViewableEmployees() {
        List<Employee> employees = getAllEmployees();
        return employees.stream().map(employee -> employeeMapper.convertToDto(employee, passwordEncoder))
                .collect(Collectors.toList());
    }

    public Employee getEmployeeById(Long id) {
        Supplier<CustomException> supplier = () -> new CustomException("Employee does not exist.");
        return employeeRepo.findById(id).orElseThrow(supplier);
    }

    public EmployeeDto getEmployeeByEmail(String email) {
        Employee employee = employeeRepo.findByEmailStartsWith(email).orElseThrow(() -> new CustomException("Employee does not exist."));
        return employeeMapper.convertToDto(employee, passwordEncoder);
    }

    private Employee getEmployeeByUsername(String username){
        Supplier<CustomException> supplier = () -> new CustomException("Employee does not exist.");
        return employeeRepo.findByUserNameStartsWith(username).orElseThrow(supplier);
    }
    public EmployeeDto getEmployeeDtoByUsername(String username) {
        Supplier<CustomException> supplier = () -> new CustomException("Employee does not exist.");
        Employee employee = employeeRepo.findByUserNameStartsWith(username).orElseThrow(supplier);
        return employeeMapper.convertToDto(employee, passwordEncoder);
    }

    public EmployeeDto getViewableEmployeeById(Long id) {
        Employee employee = getEmployeeById(id);
        return mapEmployee.apply(employee);
    }

    public EmployeeDto createViewableEmployee(EmployeeDto employeeDto, PasswordEncoder passwordEncoder) {
        Employee savedEmployee = createEmployee(employeeDto, passwordEncoder);
        return employeeMapper.convertToDto(savedEmployee, passwordEncoder);
    }

    public Employee createEmployee(EmployeeDto employeeDto, PasswordEncoder passwordEncoder) {
        performChecks(employeeDto);
        Employee employee = employeeMapper.convertToEntity(employeeDto, passwordEncoder);
        setIds(employee);
        return employeeRepo.save(employee);
    }

    public void createEmployee(EmployeeDto employeeDto){
        performChecks(employeeDto);
        Employee employee = employeeMapper.convertToEntity(employeeDto, passwordEncoder);
        employeeRepo.save(employee);
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

    private void setIds(Employee employee) {
        employee.setId(employeeSequenceGeneratorService.getEmployeeSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Employee.GENERATED_ID));
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto employeeDetails) {
        Employee employee = getEmployee(employeeRepo.findById(id));
        Employee updatedEmployee = employeeMapper.updateEmployee(employee, employeeDetails, passwordEncoder);
        employeeRepo.save(updatedEmployee);
        return employeeMapper.convertToDto(updatedEmployee, passwordEncoder);
    }

    public void updateEmployee(Long id, Employee employee) {
        EmployeeDto dto = mapEmployee.apply(employee);
        updateEmployee(id, dto);
    }

    private void setDetails(EmployeeDto employeeDto, Employee employee) {
        employee.setUserName(employeeDto.getUserName());
        employee.setRoles(employeeDto.getRoles());
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
        updateEmployee(employee.getId(), employee);
    }

    public void deleteRoleForEmployee(String role, String user){
        Employee employee = getEmployeeByUsername(user);
        roleService.deleteRoleForEmployee(role, employee);
        updateEmployee(employee.getId(), employee);
    }

    public void resetPassword(String username, String password){
        Employee employee = getEmployeeByUsername(username);
        employee.setPassword(passwordEncoder.encode(password));
    }

}