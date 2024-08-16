package com.example.performance_management.entity.role;

public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN"),
    COMPANYADMIN("COMPANYADMIN");

    private final String roleName;

    RoleEnum(String role) {
        roleName = role;
    }

    public String getRoleName() {
        return roleName;
    }

}
