package com.example.performance_management.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCompanyDetailsDto {

    // job details
    public String team;
    public Long employeeId;
    public String managerEmail;
    public String businessTitle; //sr, junior, associate
    public String jobProfile; //dev,qa
    public String location;
    public String hireDate;
    public String lengthOfService;
    public String telephone;

    public String email; // can use this... to map the employee in hrm system.
    public String workAddress;

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
