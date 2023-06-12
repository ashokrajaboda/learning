package com.gagana.hospital.api.domain;

public enum Authorities {
    ADMIN("ROLE_ADMIN"),

    MEDICAL_ADMIN("ROLE_MEDICAL_ADMIN"),
    MEDICAL_OPERATOR("ROLE_MEDICAL_OPERATOR");

    private String value;

    Authorities(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
