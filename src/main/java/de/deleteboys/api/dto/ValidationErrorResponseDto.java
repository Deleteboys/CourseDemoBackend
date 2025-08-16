package de.deleteboys.api.dto;

import java.util.Map;

public class ValidationErrorResponseDto {
    public String message;
    // Eine Map, die das Feld dem Fehler zuordnet
    public Map<String, String> fieldErrors;

    public ValidationErrorResponseDto(String message, Map<String, String> fieldErrors) {
        this.message = message;
        this.fieldErrors = fieldErrors;
    }
}