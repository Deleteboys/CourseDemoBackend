package de.deleteboys.services;

import de.deleteboys.api.dto.create.LessonCreateDto;
import de.deleteboys.api.dto.create.RatingCreateDto;
import de.deleteboys.api.dto.summary.LessonSummaryDto;
import de.deleteboys.api.dto.summary.RatingSummaryDto;
import de.deleteboys.api.dto.update.LessonUpdateDto;
import de.deleteboys.domain.Course;
import de.deleteboys.domain.Lesson;
import de.deleteboys.domain.Rating;
import de.deleteboys.domain.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;

@ApplicationScoped
public class LessonService {

    @Inject
    LogService logService;

    @Inject
    CourseService courseService;

    public Lesson getLesson(Long lessonID) {
        Lesson lesson = Lesson.findById(lessonID);
        if(lesson == null) {
            throw new NotFoundException("Lesson not found");
        }
        return lesson;
    }

    public LessonSummaryDto getLessonSummaryInCourse(Long courseId, Long lessonId) {
        return new LessonSummaryDto(getLessonInCourse(courseId, lessonId));
    }

    public Lesson getLessonInCourse(Long courseId, Long lessonId) {
        Lesson lesson = getLesson(lessonId);

        if(!lesson.course.id.equals(courseId)) {
            throw new NotFoundException("Lesson not found in course");
        }

        return lesson;
    }

    public List<LessonSummaryDto> getAllLessons() {
        List<Lesson> allLessonsFromDb = Lesson.listAll();
        return allLessonsFromDb.stream().map(LessonSummaryDto::new).toList();
    }

    @Transactional
    public LessonSummaryDto patchLesson(Long lessonId, LessonUpdateDto lessonUpdate) {
        Lesson lessonFromDb = getLesson(lessonId);
        if(lessonUpdate.title != null && !lessonUpdate.title.equals(lessonFromDb.title)) {
            logService.logEntityUpdate("lesson", lessonId, "description",lessonFromDb.title,lessonUpdate.title);
            lessonFromDb.title = lessonUpdate.title;
        }
        if(lessonUpdate.content != null && !lessonUpdate.content.equals(lessonFromDb.content)) {
            logService.logEntityUpdate("lesson", lessonId, "description",lessonFromDb.content,lessonUpdate.content);
            lessonFromDb.content = lessonUpdate.content;
        }
        if(lessonUpdate.orderIndex != 0 && lessonUpdate.orderIndex != lessonFromDb.orderIndex) {
            logService.logEntityUpdate("lesson", lessonId, "description",String.valueOf(lessonFromDb.orderIndex),String.valueOf(lessonUpdate.orderIndex));
            lessonFromDb.orderIndex = lessonUpdate.orderIndex;
        }

        if(lessonUpdate.courseId != 0) {
            Course course = courseService.getCourse(lessonUpdate.courseId);
            if(!lessonFromDb.course.equals(course)) {
                logService.logEntityUpdate("lesson", lessonId, "course",String.valueOf(lessonFromDb.course.id),String.valueOf(lessonUpdate.courseId));
                lessonFromDb.course = course;
            }
        }

        return new LessonSummaryDto(lessonFromDb);
    }

    @Transactional
    public LessonSummaryDto createLesson(LessonCreateDto lessonCreate){
        Lesson lesson = new Lesson();
        lesson.title = lessonCreate.title;
        lesson.content = lessonCreate.content;
        lesson.orderIndex = lessonCreate.orderIndex;
        lesson.course = courseService.getCourse(lessonCreate.courseId);
        lesson.persist();
        return new LessonSummaryDto(lesson);
    }

    public List<RatingSummaryDto> getRatingsOfLesson(Long courseId, Long lessonId) {
        Lesson lesson = getLessonInCourse(courseId, lessonId);
        return lesson.ratings.stream().map(RatingSummaryDto::new).toList();
    }

    @Transactional
    public RatingSummaryDto createRating(Long courseId, Long lessonId, RatingCreateDto ratingDto, User user) {
        Rating rating = new Rating();
        rating.score = ratingDto.score;
        rating.comment = ratingDto.comment;
        rating.lesson = getLessonInCourse(courseId, lessonId);
        rating.user = user;
        rating.persist();
        return new RatingSummaryDto(rating);
    }

}
