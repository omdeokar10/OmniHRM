package com.example.performance_management.entity.role;

public enum RoleUtil {
    USER("user"),
    ADMIN("admin");

    private final String value;

    RoleUtil(String roleName) {
        this.value = roleName;
    }
    public String getValue() {
        return value;
    }
}
