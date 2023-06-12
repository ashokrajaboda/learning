package com.gagana.hospital.api.config.properties;

public enum SecurityEncode {
    ARGON2("argon2"),BCRYPT("bcrypt"),PBKDF2("pbkdf2"),SCRYPT("scrypt");
    private String value;

    SecurityEncode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
