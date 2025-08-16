package de.deleteboys.api.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginDto {

    @NotBlank(message = "Benutzername darf nicht leer sein.")
    public String username;
    @NotBlank(message = "Passwort darf nicht leer sein.")
    public String password;

}
