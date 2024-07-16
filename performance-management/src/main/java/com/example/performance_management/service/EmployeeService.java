package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
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
    private final CompanyService companyService;
    private final EmployeeMapper employeeMapper = new EmployeeMapper();
    private final EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final Function<Employee, EmployeeDto> mapEmployee = (employee) -> employeeMapper.convertToDto(employee, passwordEncoder);

    public EmployeeService(EmployeeRepo employeeRepo, CompanyService companyService,
                           EmployeeSequenceGeneratorService employeeSequenceGeneratorService) {
        this.employeeRepo = employeeRepo;
        this.companyService = companyService;
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

    public EmployeeDto getViewableEmployeeById(Long id){
        Employee employee = getEmployeeById(id);
        return mapEmployee.apply(employee);
    }

    public EmployeeDto createViewableEmployee(EmployeeDto employeeDto, PasswordEncoder passwordEncoder) {
        Employee savedEmployee = createEmployee(employeeDto, passwordEncoder);
        return employeeMapper.convertToDto(savedEmployee, passwordEncoder);
    }

    private Employee createEmployee(EmployeeDto employeeDto, PasswordEncoder passwordEncoder) {
        performChecks(employeeDto);
        Employee employee = employeeMapper.convertToEntity(employeeDto, passwordEncoder);
        setIds(employee);
        Employee savedEmployee = employeeRepo.save(employee);
        return savedEmployee;
    }

    private void performChecks(EmployeeDto employeeDto) {
        if (checkIfUserExistsWithName(employeeDto.getUserName()) || checkForEmail(employeeDto.getEmail())) {
            throw new CustomException("Name already exists");
        }
        if (!companyService.checkForSameName(employeeDto.getCompanyName())) {
            throw new CustomException("Company does not exist");
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
        setDetails(employeeDetails, employee);
        Employee updatedEmployee = employeeRepo.save(employee);
        return employeeMapper.convertToDto(updatedEmployee, passwordEncoder);
    }

    public void updateEmployee(Long id, Employee employee){
        EmployeeDto dto = mapEmployee.apply(employee);
        updateEmployee(id, dto);
    }

    private void setDetails(EmployeeDto employeeDto, Employee employee) {
        employee.setCompanyName(employeeDto.getCompanyName());
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setEmail(employeeDto.getEmail());
        employee.setUserName(employeeDto.getUserName());
        employee.setFullName(employeeDto.getFullName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setRoles(employeeDto.getRoles());
        employee.setTeam(employeeDto.getTeam());
    }

    public void deleteEmployee(Long id) {
        Employee employee = getEmployee(employeeRepo.findById(id));
        employeeRepo.delete(employee);
    }

    public Employee getEmployeeByName(String user) {
        return getEmployee(employeeRepo.findByUserNameStartsWith(user));
    }

    private Employee getEmployee(Optional<Employee> user) {
        if(user.isEmpty()){
            throw new CustomException("Employee is not present");
        }
        return user.get();
    }
}