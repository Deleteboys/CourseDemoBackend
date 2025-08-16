package de.deleteboys.api.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {
    public int status;
    public String error;
    public String message;
    public LocalDateTime timestamp;
}