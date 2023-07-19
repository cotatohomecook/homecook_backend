package com.cotato.homecook.enums;

public enum Role {
    ROLE_CUSTOMER("ROLE_CUSTOMER"),
    ROLE_SELLER("ROLE_SELLER"),
    ROLE_ADMIN("ROLE_ADMIN");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String value() {
        return role;
    }
}
