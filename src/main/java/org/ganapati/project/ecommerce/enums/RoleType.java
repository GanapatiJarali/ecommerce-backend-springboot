package org.ganapati.project.ecommerce.enums;

public enum RoleType {
    ADMIN("ADMIN"),
    USER("USER"),
    SUPER_ADMIN("SUPER_ADMIN");

    private String role;

    RoleType(String role) {
        this.role = role;
    }
}
