package com.example.performance_management.mapper;


import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
import com.example.performance_management.entity.EmployeeCompanyDetails;

public class EmployeeCompanyDetailsMapper {

    public EmployeeCompanyDetails convertToEntity(EmployeeCompanyDetailsDto companyDetailsDto) {
        EmployeeCompanyDetails employeeCompanyDetails = new EmployeeCompanyDetails();
        employeeCompanyDetails.setEmployeeId(companyDetailsDto.getEmployeeId());
        employeeCompanyDetails.setTeam(companyDetailsDto.getTeam());
        employeeCompanyDetails.setManagerEmail(companyDetailsDto.getManagerEmail());
        employeeCompanyDetails.setBusinessTitle(companyDetailsDto.getBusinessTitle());
        employeeCompanyDetails.setJobProfile(companyDetailsDto.getJobProfile());
        employeeCompanyDetails.setLocation(companyDetailsDto.getLocation());
        employeeCompanyDetails.setHireDate(companyDetailsDto.getHireDate());
        employeeCompanyDetails.setTelephone(companyDetailsDto.getTelephone());
        employeeCompanyDetails.setEmail(companyDetailsDto.getEmail());
        employeeCompanyDetails.setWorkAddress(companyDetailsDto.getWorkAddress());

        employeeCompanyDetails.setGender(companyDetailsDto.getGender());
        employeeCompanyDetails.setDateOfBirth(companyDetailsDto.getDateOfBirth());
        employeeCompanyDetails.setAge(companyDetailsDto.getAge());
        employeeCompanyDetails.setCountryOfBirth(companyDetailsDto.getCountryOfBirth());
        employeeCompanyDetails.setCityOfBirth(companyDetailsDto.getCityOfBirth());
        employeeCompanyDetails.setMaritalStatus(companyDetailsDto.getMaritalStatus());
        employeeCompanyDetails.setNationality(companyDetailsDto.getNationality());
        employeeCompanyDetails.setDependantName(companyDetailsDto.getDependantName());
        employeeCompanyDetails.setRelationToDependant(companyDetailsDto.getRelationToDependant());

        employeeCompanyDetails.setBaseSalary(getAnInt(companyDetailsDto.getBaseSalary()));
        employeeCompanyDetails.setBonusAllotted(Integer.parseInt(companyDetailsDto.getBonusAllotted()));
        employeeCompanyDetails.setStocksOffered(Integer.parseInt(companyDetailsDto.getStocksOffered()));

        return employeeCompanyDetails;

    }

    public void updateEntity(EmployeeCompanyDetails employeeCompanyDetails, EmployeeCompanyDetailsDto companyDetailsDto) {

        if (companyDetailsDto.getTeam() != null && !companyDetailsDto.getTeam().isEmpty()) {
            employeeCompanyDetails.setTeam(companyDetailsDto.getTeam());
        }
        if (companyDetailsDto.getManagerEmail()!=null && !companyDetailsDto.getManagerEmail().isEmpty()) {
            employeeCompanyDetails.setManagerEmail(companyDetailsDto.getManagerEmail());
        }
        if (companyDetailsDto.getBusinessTitle() != null && !companyDetailsDto.getBusinessTitle().isEmpty()) {
            employeeCompanyDetails.setBusinessTitle(companyDetailsDto.getBusinessTitle());
        }
        if (companyDetailsDto.getJobProfile() != null && !companyDetailsDto.getJobProfile().isEmpty()) {
            employeeCompanyDetails.setJobProfile(companyDetailsDto.getJobProfile());
        }

        if (companyDetailsDto.getLocation() != null && !companyDetailsDto.getLocation().isEmpty()) {
            employeeCompanyDetails.setLocation(companyDetailsDto.getLocation());
        }

        if (companyDetailsDto.getHireDate() != null) {
            employeeCompanyDetails.setHireDate(companyDetailsDto.getHireDate());
        }

        if (companyDetailsDto.getTelephone() != null && !companyDetailsDto.getTelephone().isEmpty()) {
            employeeCompanyDetails.setTelephone(companyDetailsDto.getTelephone());
        }

        if (companyDetailsDto.getEmail() != null && !companyDetailsDto.getEmail().isEmpty()) {
            employeeCompanyDetails.setEmail(companyDetailsDto.getEmail());
        }

        if (companyDetailsDto.getWorkAddress() != null && !companyDetailsDto.getWorkAddress().isEmpty()) {
            employeeCompanyDetails.setWorkAddress(companyDetailsDto.getWorkAddress());
        }

        if (companyDetailsDto.getGender() != null && !companyDetailsDto.getGender().isEmpty()) {
            employeeCompanyDetails.setGender(companyDetailsDto.getGender());
        }

        if (companyDetailsDto.getDateOfBirth() != null) {
            employeeCompanyDetails.setDateOfBirth(companyDetailsDto.getDateOfBirth());
        }

        if (companyDetailsDto.getAge() != null) {
            employeeCompanyDetails.setAge(companyDetailsDto.getAge());
        }

        if (companyDetailsDto.getCountryOfBirth() != null && !companyDetailsDto.getCountryOfBirth().isEmpty()) {
            employeeCompanyDetails.setCountryOfBirth(companyDetailsDto.getCountryOfBirth());
        }

        if (companyDetailsDto.getCityOfBirth() != null && !companyDetailsDto.getCityOfBirth().isEmpty()) {
            employeeCompanyDetails.setCityOfBirth(companyDetailsDto.getCityOfBirth());
        }

        if (companyDetailsDto.getMaritalStatus() != null && !companyDetailsDto.getMaritalStatus().isEmpty()) {
            employeeCompanyDetails.setMaritalStatus(companyDetailsDto.getMaritalStatus());
        }

        if (companyDetailsDto.getNationality() != null && !companyDetailsDto.getNationality().isEmpty()) {
            employeeCompanyDetails.setNationality(companyDetailsDto.getNationality());
        }

        if (companyDetailsDto.getDependantName() != null && !companyDetailsDto.getDependantName().isEmpty()) {
            employeeCompanyDetails.setDependantName(companyDetailsDto.getDependantName());
        }

        if (companyDetailsDto.getRelationToDependant() != null && !companyDetailsDto.getRelationToDependant().isEmpty()) {
            employeeCompanyDetails.setRelationToDependant(companyDetailsDto.getRelationToDependant());
        }

        if (validityCheck(companyDetailsDto.getBaseSalary()) && checkIfContainsOnlyDigits(companyDetailsDto.getBaseSalary())) {
            employeeCompanyDetails.setBaseSalary(getAnInt(companyDetailsDto.getBaseSalary()));
        }

        if (validityCheck(companyDetailsDto.getBonusAllotted()) && checkIfContainsOnlyDigits(companyDetailsDto.getBonusAllotted())) {
            employeeCompanyDetails.setBonusAllotted(getAnInt(companyDetailsDto.getBonusAllotted()));
        }

        if (validityCheck(companyDetailsDto.getStocksOffered()) && checkIfContainsOnlyDigits(companyDetailsDto.getStocksOffered())) {
            employeeCompanyDetails.setStocksOffered(getAnInt(companyDetailsDto.getStocksOffered()));
        }

        if (validityCheck(companyDetailsDto.getTotalComp()) && checkIfContainsOnlyDigits(companyDetailsDto.getTotalComp())) {
            employeeCompanyDetails.setTotalComp(getAnInt(companyDetailsDto.getTotalComp()));
        }

    }

    public int getAnInt(String entity) {
        return Integer.parseInt(entity);
    }

    public boolean validityCheck(String baseSalary) {
        return baseSalary != null && !baseSalary.isEmpty();
    }

    private boolean checkIfContainsOnlyDigits(String baseSalary) {
        char[] chars = baseSalary.toCharArray();
        for(char c: chars){
            if(!Character.isDigit(c)){
                return false;
            }
        }
        return true;
    }

    public EmployeeCompanyDetailsDto convertToDto(EmployeeCompanyDetails entity) {
        EmployeeCompanyDetailsDto dto = new EmployeeCompanyDetailsDto();

        dto.setTeam(entity.getTeam());
        dto.setEmployeeId(entity.getEmployeeId());
        dto.setBusinessTitle(entity.getBusinessTitle());
        dto.setJobProfile(entity.getJobProfile());
        dto.setLocation(entity.getLocation());
        dto.setHireDate(entity.getHireDate());
        dto.setLengthOfService(entity.getLengthOfService());
        dto.setTelephone(entity.getTelephone());
        dto.setEmail(entity.getEmail());
        dto.setWorkAddress(entity.getWorkAddress());
        dto.setManagerEmail(entity.getManagerEmail());

        dto.setGender(entity.getGender());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setAge(entity.getAge());
        dto.setCountryOfBirth(entity.getCountryOfBirth());
        dto.setCityOfBirth(entity.getCityOfBirth());
        dto.setMaritalStatus(entity.getMaritalStatus());
        dto.setNationality(entity.getNationality());
        dto.setDependantName(entity.getDependantName());
        dto.setRelationToDependant(entity.getRelationToDependant());

        dto.setBaseSalary(getString(entity.getBaseSalary()));
        dto.setBonusAllotted(getString(entity.getBonusAllotted()));
        dto.setStocksOffered(getString(entity.getStocksOffered()));
        dto.setTotalComp(getString(entity.getTotalComp()));

        return dto;
    }

    private static String getString(int entity) {
        return String.valueOf(entity);
    }


}
