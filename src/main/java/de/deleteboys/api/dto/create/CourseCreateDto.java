package de.deleteboys.api.dto.create;

import jakarta.validation.constraints.NotBlank;

public class CourseCreateDto {

    @NotBlank(message = "Titel darf nicht leer sein")
    public String title;
    @NotBlank(message = "Beschreibung darf nicht leer sein")
    public String description;

}
