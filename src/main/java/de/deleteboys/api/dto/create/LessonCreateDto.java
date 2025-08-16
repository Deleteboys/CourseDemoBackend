package de.deleteboys.api.dto.create;

import jakarta.validation.constraints.NotBlank;

public class LessonCreateDto {

    @NotBlank(message = "Titel darf nicht leer sein.")
    public String title;
    @NotBlank(message = "Inhalt darf nicht leer sein.")
    public String content;
    @NotBlank(message = "Ordnungindex darf nicht leer sein.")
    public int orderIndex;
    @NotBlank(message = "Kurs-ID darf nicht leer sein.")
    public long courseId;


}
