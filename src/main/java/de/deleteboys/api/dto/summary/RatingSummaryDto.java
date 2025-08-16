package de.deleteboys.api.dto.summary;

import de.deleteboys.domain.Rating;

import java.time.LocalDateTime;

public class RatingSummaryDto {

    public Long id;
    public int score;
    public String comment;
    public String user;
    public String lesson;
    public LocalDateTime createdAt;

    public RatingSummaryDto(Rating rating) {
        this.id = rating.id;
        this.score = rating.score;
        this.comment = rating.comment;
        this.user = rating.user.username;
        this.lesson = rating.lesson.title;
        this.createdAt = rating.createdAt;
    }

}
