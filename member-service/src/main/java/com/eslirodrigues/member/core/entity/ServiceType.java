package com.eslirodrigues.member.core.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ServiceType {
    FREE("free"),
    HALF_PRICE("half-price"),
    FULL_PRICE("full-price");

    private final String value;

    ServiceType(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ServiceType fromValue(String value) {
        for (ServiceType type : ServiceType.values()) {
            if (type.value.equalsIgnoreCase(value) || type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown service type: " + value);
    }
}
