package com.example.performance_management.dto;

import com.example.performance_management.entity.role.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCompanyDetailsDto {

    //initial password.
    private String password;
    private List<Role> roles;

    // job details
    public String team;
    public String managerEmail;
    public Long employeeId;
    public String businessTitle; //sr, junior, associate
    public String jobProfile; //dev,qa
    public String location;
    public String hireDate;
    public String lengthOfService;
    public String telephone;

    public String email; // can use this... to map the employee in hrm system.
    public String userName;
    public String fullName;
    public String workAddress;

    public String companyName;


    //personal details.
    public String gender;
    public String dateOfBirth;
    public String age;
    public String countryOfBirth;
    public String cityOfBirth;
    public String maritalStatus;
    public String nationality;
    public String dependantName;
    public String relationToDependant;

    // salary details.
    public String baseSalary;
    public String bonusAllotted;
    public String stocksOffered;
    public String totalComp;

}
