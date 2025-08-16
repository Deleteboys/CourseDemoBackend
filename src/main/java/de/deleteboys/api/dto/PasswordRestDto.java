package de.deleteboys.api.dto;

import jakarta.validation.constraints.NotBlank;

public class PasswordRestDto {
    @NotBlank(message = "Das Passwort darf nicht leer sein.")
    public String password;
}
