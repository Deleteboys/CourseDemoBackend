package de.deleteboys.services;

import de.deleteboys.api.dto.create.CourseCreateDto;
import de.deleteboys.api.dto.summary.CourseSummaryDto;
import de.deleteboys.api.dto.update.CourseUpdateDto;
import de.deleteboys.api.dto.summary.LessonSummaryDto;
import de.deleteboys.domain.Course;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class CourseService {

    @Inject
    LogService logService;

    public List<LessonSummaryDto> getAllLessonsOfCourse(Long courseId) {
        Course course = Course.findById(courseId);
        if(course == null) {
            throw new NotFoundException("Course not found");
        }
        return course.lessons.stream().map(LessonSummaryDto::new).toList();
    }

    public CourseSummaryDto getCourseSummary(Long courseId) {
        return new CourseSummaryDto(getCourse(courseId));
    }

    public Course getCourse(Long courseId) {
        Course course = Course.findById(courseId);
        if(course == null) {
            throw new NotFoundException("Course not found");
        }
        return course;
    }

    @Transactional
    public CourseSummaryDto patchCourse(Long courseId, CourseUpdateDto dto) {
        Course course = getCourse(courseId);
        if(!course.title.equals(dto.title) && dto.title != null) {
            logService.logEntityUpdate("course", courseId, "title",course.title,dto.title);
            course.title = dto.title;
        }
        if(!course.description.equals(dto.description) && dto.description != null) {
            logService.logEntityUpdate("course", courseId, "description",course.description,dto.description);
            course.description = dto.description;
        }
        return new CourseSummaryDto(course);
    }

    @Transactional
    public CourseSummaryDto createCourse(CourseCreateDto courseCreate) {
        Course course = new Course();
        course.title = courseCreate.title;
        course.description = courseCreate.description;
        course.persist();
        return new CourseSummaryDto(course);
    }

}
