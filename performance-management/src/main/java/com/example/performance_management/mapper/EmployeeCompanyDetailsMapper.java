package com.example.performance_management.mapper;


import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.Employee;
import com.example.performance_management.entity.EmployeeCompanyDetails;

import java.util.List;

public class EmployeeCompanyDetailsMapper {

    public EmployeeDto convertToEmployeeDto(EmployeeCompanyDetailsDto companyDetailsDto){
        EmployeeDto dto = new EmployeeDto();
        dto.setUserName(companyDetailsDto.getUserName());
        dto.setPassword(companyDetailsDto.getUserName());
        dto.setId(companyDetailsDto.getEmployeeId());
        dto.setRoles(companyDetailsDto.getRoles());
        dto.setCompanyName(companyDetailsDto.getCompanyName());
        dto.setEmail(companyDetailsDto.getEmail());
        return dto;
    }

    public EmployeeCompanyDetails convertToEntity(EmployeeCompanyDetailsDto dto) {
        EmployeeCompanyDetails entity = new EmployeeCompanyDetails();

        entity.setPassword(dto.getPassword());
        entity.setRoles(dto.getRoles());
        entity.setUserName(dto.getUserName());

        entity.setEmployeeId(dto.getEmployeeId());
        entity.setTeam(dto.getTeam());
        entity.setManagerEmail(dto.getManagerEmail());
        entity.setBusinessTitle(dto.getBusinessTitle());
        entity.setJobProfile(dto.getJobProfile());
        entity.setLocation(dto.getLocation());
        entity.setHireDate(dto.getHireDate());
        entity.setTelephone(dto.getTelephone());
        entity.setEmail(dto.getEmail());
        entity.setFullName(dto.getFullName());
        entity.setWorkAddress(dto.getWorkAddress());
        entity.setCompanyName(dto.getCompanyName());

        entity.setGender(dto.getGender());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setAge(dto.getAge());
        entity.setCountryOfBirth(dto.getCountryOfBirth());
        entity.setCityOfBirth(dto.getCityOfBirth());
        entity.setMaritalStatus(dto.getMaritalStatus());
        entity.setNationality(dto.getNationality());
        entity.setDependantName(dto.getDependantName());
        entity.setRelationToDependant(dto.getRelationToDependant());

        entity.setBaseSalary(getAnInt(dto.getBaseSalary()));
        entity.setBonusAllotted(getAnInt(dto.getBonusAllotted()));
        entity.setStocksOffered(getAnInt(dto.getStocksOffered()));

        return entity;

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

        if(companyDetailsDto.getFullName()!=null && !companyDetailsDto.getFullName().isEmpty()){
            employeeCompanyDetails.setFullName(companyDetailsDto.getFullName());
        }

        if(companyDetailsDto.getCompanyName()!=null && !companyDetailsDto.getCompanyName().isEmpty()){
            employeeCompanyDetails.setCompanyName(companyDetailsDto.getCompanyName());
        }

        if(companyDetailsDto.getRoles()!=null && !companyDetailsDto.getCompanyName().isEmpty()){
            employeeCompanyDetails.setRoles(companyDetailsDto.getRoles());
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

        if (companyDetailsDto.getUserName() != null && !companyDetailsDto.getUserName().isEmpty()) {
            employeeCompanyDetails.setUserName(companyDetailsDto.getUserName());
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

        int totalComp = 0;
        if (validityCheck(companyDetailsDto.getBaseSalary()) && checkIfContainsOnlyDigits(companyDetailsDto.getBaseSalary())) {
            int baseSalary = getAnInt(companyDetailsDto.getBaseSalary());
            employeeCompanyDetails.setBaseSalary(baseSalary);
            totalComp += baseSalary;
        }

        if (validityCheck(companyDetailsDto.getBonusAllotted()) && checkIfContainsOnlyDigits(companyDetailsDto.getBonusAllotted())) {
            int bonus = getAnInt(companyDetailsDto.getBonusAllotted());
            employeeCompanyDetails.setBonusAllotted(bonus);
            totalComp += bonus;
        }

        if (validityCheck(companyDetailsDto.getStocksOffered()) && checkIfContainsOnlyDigits(companyDetailsDto.getStocksOffered())) {
            int stocks = getAnInt(companyDetailsDto.getStocksOffered());
            employeeCompanyDetails.setStocksOffered(stocks);
            totalComp += stocks;
        }

        if (validityCheck(companyDetailsDto.getTotalComp()) && checkIfContainsOnlyDigits(companyDetailsDto.getTotalComp())) {
            int totalCompDto = getAnInt(companyDetailsDto.getTotalComp());
            employeeCompanyDetails.setTotalComp(totalCompDto);
        }

        if(employeeCompanyDetails.getTotalComp()!=totalComp){
            employeeCompanyDetails.setTotalComp(totalComp);
        }

    }

    public int getAnInt(String entity) {
        if(entity==null||entity.isEmpty()){
            return 0;
        }
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
        dto.setUserName(entity.getUserName());
        dto.setWorkAddress(entity.getWorkAddress());
        dto.setManagerEmail(entity.getManagerEmail());
        dto.setCompanyName(entity.getCompanyName());
        dto.setFullName(entity.getFullName());
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
