package de.deleteboys.api.dto.summary;

import de.deleteboys.domain.Lesson;

import java.time.LocalDateTime;

public class LessonSummaryDto {

    public Long id;
    public String title;
    public String content;
    public int orderIndex;
    public LocalDateTime createdAt;
    public long courseId;

    public LessonSummaryDto(Lesson lesson) {
        this.id = lesson.id;
        this.title = lesson.title;
        this.content = lesson.content;
        this.orderIndex = lesson.orderIndex;
        this.createdAt = lesson.createdAt;
        this.courseId = lesson.course.id;
    }

}
