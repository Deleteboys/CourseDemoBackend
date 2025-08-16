package de.deleteboys.api.dto;

import java.util.Map;

public class ValidationErrorResponseDto {
    public String message;
    public Map<String, String> fieldErrors;

    public ValidationErrorResponseDto(String message, Map<String, String> fieldErrors) {
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}