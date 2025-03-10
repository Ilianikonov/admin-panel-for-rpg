package com.example.demo.filter;

public enum PlayerOrder {
    ID("id"), // default
    NAME("name"),
    EXPERIENCE("experience"),
    BIRTHDAY("birthday"),
    LEVEL("level");

    private final String fieldName;

    PlayerOrder(String fieldName) {
        if (fieldName.equals(null)) {
            this.fieldName = "id";
        } else {
            this.fieldName = fieldName;
        }
    }

    public String getFieldName() {
        return fieldName;
    }
}