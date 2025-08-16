package de.deleteboys.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordChangeDto {
    @NotBlank
    public String currentPassword;

    @NotBlank
    @Size(min = 3, message = "Das neue Passwort muss mindestens 3 Zeichen lang sein.")
    public String newPassword;
}
