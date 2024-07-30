package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.dto.EmployeeHierarchyDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.EmployeeCompanyDetails;
import com.example.performance_management.exception.CustomException;
import com.example.performance_management.mapper.EmployeeCompanyDetailsMapper;
import com.example.performance_management.mongoidgen.EmployeeSequenceGeneratorService;
import com.example.performance_management.repo.EmployeeCompanyRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeCompanyDetailsService {

    private final EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    private final EmployeeCompanyRepo employeeCompanyRepo;
    private final EmployeeCompanyDetailsMapper mapper = new EmployeeCompanyDetailsMapper();
    private final EmployeeService employeeService;

    public EmployeeCompanyDetailsService(EmployeeSequenceGeneratorService employeeSequenceGeneratorService, EmployeeCompanyRepo employeeCompanyRepo, EmployeeService employeeService) {
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
        this.employeeCompanyRepo = employeeCompanyRepo;
        this.employeeService = employeeService;
    }

    public EmployeeCompanyDetailsDto getDetailsForEmployee(String email) {
        EmployeeCompanyDetails employeeCompanyDetails = getEmployeeCompanyDetails(employeeCompanyRepo.findByEmail(email));
        return mapper.convertToDto(employeeCompanyDetails);
    }

    public EmployeeCompanyDetailsDto getDetailsForEmployeeById(Long id) {
        EmployeeCompanyDetails employeeCompanyDetails = getEmployeeCompanyDetails(employeeCompanyRepo.findById(id));
        return mapper.convertToDto(employeeCompanyDetails);
    }

    public void saveDetailsForEmployee(EmployeeCompanyDetailsDto employeeCompanyDetailsDto) {
        EmployeeCompanyDetails employeeCompanyDetails = mapper.convertToEntity(employeeCompanyDetailsDto);
        employeeCompanyDetails.setEmployeeId(generateId());
        employeeCompanyDetails.setLengthOfService("0");
        if (mapper.validityCheck(employeeCompanyDetailsDto.getBaseSalary()) && mapper.validityCheck(employeeCompanyDetailsDto.getBonusAllotted())) {
            employeeCompanyDetails.setTotalComp(mapper.getAnInt(employeeCompanyDetailsDto.getBaseSalary()) + mapper.getAnInt(employeeCompanyDetailsDto.getBonusAllotted()));
        }
        employeeCompanyRepo.save(employeeCompanyDetails);
    }

    private long generateId() {
        return employeeSequenceGeneratorService.getEmployeeSequenceNumber
                (Employee.ID_KEY, Employee.ID_VAL, Employee.GENERATED_ID);
    }

    public void updateDetailsForEmployee(Long id, EmployeeCompanyDetailsDto employeeCompanyDetailsDto) {
        EmployeeCompanyDetails employeeCompanyDetails = getEmployeeCompanyDetails(employeeCompanyRepo.findById(id));
        mapper.updateEntity(employeeCompanyDetails, employeeCompanyDetailsDto);
        employeeCompanyRepo.save(employeeCompanyDetails);
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

    public List<EmployeeHierarchyDto> getHierarchy(String email) {

        EmployeeCompanyDetailsDto employeeDetails = getDetailsForEmployee(email);
        EmployeeDto employee = employeeService.getEmployeeByEmail(email);
        List<EmployeeHierarchyDto> list = new ArrayList<>();

        while (true) {
            String managerEmail = employeeDetails.getManagerEmail();
            if (managerEmail == null || managerEmail.isEmpty()) {
                break;
            }
            EmployeeDto manager = employeeService.getEmployeeByEmail(managerEmail);
            list.add(new EmployeeHierarchyDto(employee.getFullName(), employee.getEmail(), manager.getFirstName(), manager.getEmail()));
            employeeDetails = getDetailsForEmployee(manager.getEmail());
            employee = employeeService.getEmployeeByEmail(manager.getEmail());
        }

return list;


    }

}
