package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.dto.EmployeeHierarchyDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.EmployeeCompanyDetails;
import com.example.performance_management.entity.role.RoleUtil;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.EmployeeCompanyDetailsMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.EmployeeCompanyRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeCompanyDetailsService {

    private final EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    private final EmployeeCompanyRepo employeeCompanyRepo;
    private final EmployeeService employeeService;
    private final EmployeeCompanyDetailsMapper mapper = new EmployeeCompanyDetailsMapper();
    private final RoleService roleService;

    public EmployeeCompanyDetailsService(EmployeeSequenceGeneratorService employeeSequenceGeneratorService, EmployeeCompanyRepo employeeCompanyRepo, EmployeeService employeeService, RoleService roleService) {
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
        this.employeeCompanyRepo = employeeCompanyRepo;
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    public EmployeeCompanyDetailsDto getDetailsForEmployeeByUsername(String username) {
        EmployeeCompanyDetails employeeCompanyDetails = getEmployeeCompanyDetails(employeeCompanyRepo.findByUserName(username));
        return mapper.convertToDto(employeeCompanyDetails);
    }

    public EmployeeCompanyDetailsDto getDetailsForEmployeeByEmail(String email) {
        EmployeeCompanyDetails employeeCompanyDetails = getEmployeeCompanyDetails(employeeCompanyRepo.findByEmail(email));
        return mapper.convertToDto(employeeCompanyDetails);
    }

    public EmployeeCompanyDetailsDto getDetailsForEmployeeById(Long id) {
        EmployeeCompanyDetails employeeCompanyDetails = getEmployeeCompanyDetails(employeeCompanyRepo.findById(id));
        return mapper.convertToDto(employeeCompanyDetails);
    }

    public void createEmployeeForCompany(EmployeeCompanyDetailsDto employeeCompanyDetailsDto) {

        long id = generateId();

        EmployeeCompanyDetails employeeCompanyDetails = mapper.convertToEntity(employeeCompanyDetailsDto);
        employeeCompanyDetails.setEmployeeId(id);
        employeeCompanyDetails.setRoles(List.of(roleService.getRole(RoleUtil.USER)));
        employeeCompanyDetails.setPassword(employeeCompanyDetails.getUserName());
        setSalary(employeeCompanyDetailsDto, employeeCompanyDetails);
        employeeCompanyRepo.save(employeeCompanyDetails);

        EmployeeDto dto = mapper.convertToEmployeeDto(employeeCompanyDetailsDto);
        dto.setId(id);
        dto.setRoles(List.of(roleService.getRole(RoleUtil.USER)));
        employeeService.createEmployee(dto);
    }

    private void setSalary(EmployeeCompanyDetailsDto employeeCompanyDetailsDto, EmployeeCompanyDetails employeeCompanyDetails) {
        if (mapper.validityCheck(employeeCompanyDetailsDto.getBaseSalary()) && mapper.validityCheck(employeeCompanyDetailsDto.getBonusAllotted())) {
            employeeCompanyDetails.setTotalComp(mapper.getAnInt(employeeCompanyDetailsDto.getBaseSalary()) + mapper.getAnInt(employeeCompanyDetailsDto.getBonusAllotted()));
        }
    }

    private long generateId() {
        return employeeSequenceGeneratorService.getEmployeeSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Employee.GENERATED_ID);
    }

    public void updateDetailsForEmployee(Long id, EmployeeCompanyDetailsDto employeeCompanyDetailsDto) {
        EmployeeCompanyDetails employeeCompanyDetails = getEmployeeCompanyDetails(employeeCompanyRepo.findById(id));
        mapper.updateEntity(employeeCompanyDetails, employeeCompanyDetailsDto);
        employeeCompanyRepo.save(employeeCompanyDetails);

        EmployeeDto employeeDto = mapper.convertToEmployeeDto(employeeCompanyDetailsDto);
        employeeService.updateEmployee(id, employeeDto);
    }

    public void deleteEmployeeDetails(Long id) {
        employeeCompanyRepo.deleteById(id);
    }

    private EmployeeCompanyDetails getEmployeeCompanyDetails(Optional<EmployeeCompanyDetails> employeeCompanyDetails) {
        if (employeeCompanyDetails.isEmpty()) {
            throw new CustomException("Employee Company Details are empty.");
        }
        return employeeCompanyDetails.get();

    }

    public List<EmployeeHierarchyDto> getHierarchy(String username) {

        List<EmployeeHierarchyDto> list = new ArrayList<>();

        EmployeeCompanyDetailsDto employeeDetails = getDetailsForEmployeeByUsername(username);
        while (!Objects.isNull(employeeDetails.getManagerEmail()) && employeeDetails.getManagerEmail().contains("@")) {
            EmployeeCompanyDetailsDto managerDetails = getDetailsForEmployeeByEmail(employeeDetails.getManagerEmail());
            list.add(new EmployeeHierarchyDto(employeeDetails.getUserName(), employeeDetails.getEmail(),
                    managerDetails.getUserName(), managerDetails.getEmail()));
            employeeDetails = getDetailsForEmployeeByUsername(managerDetails.getUserName());
            System.out.println(employeeDetails);
        }
        return list;
    }

    public List<EmployeeCompanyDetailsDto> getEmployeesByCompany(String companyName) {
        return employeeCompanyRepo.findAllByCompanyName(companyName).stream().map(entity -> mapper.convertToDto(entity)).collect(Collectors.toList());
    }



}
