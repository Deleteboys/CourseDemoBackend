package de.deleteboys.api.dto;

import de.deleteboys.domain.Course;

public class CourseSummaryDto {

    public Long id;
    public String title;
    public String description;

    public CourseSummaryDto(Course course) {
        this.id = course.id;
        this.title = course.title;
        this.description = course.description;
    }

    public CourseSummaryDto(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}