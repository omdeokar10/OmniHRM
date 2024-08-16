package com.example.performance_management.mapper;


import com.example.performance_management.dto.EmployeeCompanyDetailsDto;
import com.example.performance_management.dto.EmployeeDto;
import com.example.performance_management.entity.EmployeeCompanyDetails;
import com.example.performance_management.utils.DetailsSetUtils;

public class EmployeeCompanyDetailsMapper {

    private final DetailsSetUtils detailsSetUtils = new DetailsSetUtils();
    private final EmployeeMapper employeeMapper = new EmployeeMapper();

    public EmployeeDto convertToEmployeeDto(EmployeeCompanyDetails dto) {
        EmployeeDto entity = new EmployeeDto();
        detailsSetUtils.setIfNotNull(dto::getUserName, entity::setUserName);
        detailsSetUtils.setIfNotNull(dto::getPassword, entity::setPassword);
        detailsSetUtils.setIfNotNull(dto::getEmployeeId, entity::setId);
        detailsSetUtils.setIfNotNull(dto::getRoles, entity::setRoles);
        detailsSetUtils.setIfNotNull(dto::getCompanyName, entity::setCompanyName);
        detailsSetUtils.setIfNotNull(dto::getEmail, entity::setEmail);
        return entity;
    }

    public EmployeeCompanyDetails convertToEmployeeCompanyDetails(EmployeeCompanyDetailsDto dto, EmployeeCompanyDetails entity) {

        detailsSetUtils.setIfNotNull(dto::getPassword, entity::setPassword);
        detailsSetUtils.setIfNotNull(dto::getRoles, entity::setRoles);
        detailsSetUtils.setIfNotNull(dto::getUserName, entity::setUserName);

        detailsSetUtils.setIfNotNull(dto::getEmployeeId, entity::setEmployeeId);
        detailsSetUtils.setIfNotNull(dto::getTeam, entity::setTeam);
        detailsSetUtils.setIfNotNull(dto::getManagerEmail, entity::setManagerEmail);
        detailsSetUtils.setIfNotNull(dto::getBusinessTitle, entity::setBusinessTitle);
        detailsSetUtils.setIfNotNull(dto::getJobProfile, entity::setJobProfile);
        detailsSetUtils.setIfNotNull(dto::getLocation, entity::setLocation);
        detailsSetUtils.setIfNotNull(dto::getHireDate, entity::setHireDate);
        detailsSetUtils.setIfNotNull(dto::getTelephone, entity::setTelephone);
        detailsSetUtils.setIfNotNull(dto::getEmail, entity::setEmail);
        detailsSetUtils.setIfNotNull(dto::getFullName, entity::setFullName);
        detailsSetUtils.setIfNotNull(dto::getWorkAddress, entity::setWorkAddress);
        detailsSetUtils.setIfNotNull(dto::getCompanyName, entity::setCompanyName);

        detailsSetUtils.setIfNotNull(dto::getGender, entity::setGender);
        detailsSetUtils.setIfNotNull(dto::getDateOfBirth, entity::setDateOfBirth);
        detailsSetUtils.setIfNotNull(dto::getAge, entity::setAge);
        detailsSetUtils.setIfNotNull(dto::getCountryOfBirth, entity::setCountryOfBirth);
        detailsSetUtils.setIfNotNull(dto::getCityOfBirth, entity::setCityOfBirth);
        detailsSetUtils.setIfNotNull(dto::getMaritalStatus, entity::setMaritalStatus);
        detailsSetUtils.setIfNotNull(dto::getNationality, entity::setNationality);
        detailsSetUtils.setIfNotNull(dto::getDependantName, entity::setDependantName);
        detailsSetUtils.setIfNotNull(dto::getRelationToDependant, entity::setRelationToDependant);

        detailsSetUtils.setIfNotNull(dto::getBaseSalary, salary -> entity.setBaseSalary(check(salary)));
        detailsSetUtils.setIfNotNull(dto::getBonusAllotted, bonus -> entity.setBonusAllotted(check(bonus)));
        detailsSetUtils.setIfNotNull(dto::getStocksOffered, stocks -> entity.setStocksOffered(check(stocks)));


        return entity;

    }

    public EmployeeCompanyDetails updateEntity(EmployeeCompanyDetailsDto dto) {
        EmployeeCompanyDetails entity = new EmployeeCompanyDetails();
        entity = convertToEmployeeCompanyDetails(dto, entity);
        int totalComp = entity.getBaseSalary() + entity.getBonusAllotted() + entity.getStocksOffered();
        if (entity.getTotalComp() != totalComp) {
            entity.setTotalComp(totalComp);
        }
        
        return entity;
    }

    public int check(String entity) {
        if (!validityCheck(entity)) {
            return 0;
        }
        return Integer.parseInt(entity);
    }

    public boolean validityCheck(String baseSalary) {
        return baseSalary != null && !baseSalary.isEmpty() && checkIfContainsOnlyDigits(baseSalary);
    }

    private boolean checkIfContainsOnlyDigits(String baseSalary) {
        char[] chars = baseSalary.toCharArray();
        for (char c : chars) {
            if (!Character.isDigit(c)) {
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
