package com.example.performance_management.service;

import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
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
import java.util.Objects;
import java.util.Optional;

@Service
public class EmployeeCompanyDetailsService {

    private final EmployeeSequenceGeneratorService employeeSequenceGeneratorService;
    private final EmployeeCompanyRepo employeeCompanyRepo;
    private final EmployeeCompanyDetailsMapper mapper = new EmployeeCompanyDetailsMapper();

    public EmployeeCompanyDetailsService(EmployeeSequenceGeneratorService employeeSequenceGeneratorService, EmployeeCompanyRepo employeeCompanyRepo) {
        this.employeeSequenceGeneratorService = employeeSequenceGeneratorService;
        this.employeeCompanyRepo = employeeCompanyRepo;
    }

    public EmployeeCompanyDetailsDto getDetailsForEmployeeByUsername(String username) {
        EmployeeCompanyDetails employeeCompanyDetails = getEmployeeCompanyDetails(employeeCompanyRepo.findByUsername(username));
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

    public List<EmployeeHierarchyDto> getHierarchy(String username) {

        List<EmployeeHierarchyDto> list = new ArrayList<>();

        EmployeeCompanyDetailsDto employeeDetails = getDetailsForEmployeeByUsername(username);
        while (!Objects.isNull(employeeDetails.getManagerEmail())) {

            EmployeeCompanyDetailsDto managerDetails = getDetailsForEmployeeByEmail(employeeDetails.getManagerEmail());
            list.add(new EmployeeHierarchyDto(employeeDetails.getUsername(), employeeDetails.getEmail(),
                    managerDetails.getUsername(), managerDetails.getEmail()));
            employeeDetails = getDetailsForEmployeeByUsername(managerDetails.getUsername());
            System.out.println(employeeDetails);
        }
        return list;
    }

}
