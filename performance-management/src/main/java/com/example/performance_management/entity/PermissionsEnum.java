package com.example.performance_management.entity;


import static com.example.performance_management.utils.ConstantUtils.ADMIN_ROLE;
import static com.example.performance_management.utils.ConstantUtils.USER_ROLE;

public enum PermissionsEnum {

    USER(USER_ROLE),
    STRUCTURE_READ_AUTHORITY("structure:read,company:read"),
    STRUCTURE_EDIT_AUTHORITY("structure:read,structure:update,company:read,company:update,company:create,company:delete"),
    STRUCTURE_ADMIN_AUTHORITY("structure:read,structure:update,structure:create,structure:delete,company:read,company:update,company:create,company:delete"),

    COMPANY_READ_AUTHORITY("company:read"),
    COMPANY_EDIT_AUTHORITY("company:read,company:update"),
    COMPANY_ADMIN_AUTHORITY("company:read,company:update,company:create,company:delete"),
    ADMIN(ADMIN_ROLE);

    private final String value;

    PermissionsEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}