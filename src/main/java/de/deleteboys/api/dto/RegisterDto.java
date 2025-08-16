package de.deleteboys.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterDto {

    @NotBlank(message = "Benutzername darf nicht leer sein.")
    @Size(min = 3, message = "Benutzername muss mindestens 3 Zeichen lang sein.")
    public String username;

    @NotBlank(message = "E-Mail darf nicht leer sein.")
    @Email(message = "E-Mail-Adresse hat ein ungültiges Format.")
    public String email;

    @NotBlank(message = "Passwort darf nicht leer sein.")
    @Size(min = 3, message = "Passwort muss mindestens 8 Zeichen lang sein.")
    public String password;

}
