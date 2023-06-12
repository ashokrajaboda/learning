package com.gagana.hospital.api.view;

public enum ErrorType {
    ERROR("error"),WARN("warn"),INFO("info");
    private String value;

    ErrorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
