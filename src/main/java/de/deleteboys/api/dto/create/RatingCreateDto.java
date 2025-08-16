package de.deleteboys.api.dto.create;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class RatingCreateDto {

    @NotNull(message = "Score darf nicht leer sein")
    @Min(value = 1, message = "Die Bewertung muss mindestens 1 sein.")
    @Max(value = 5, message = "Die Bewertung darf höchstens 5 sein.")
    public int score;
    public String comment;

}
