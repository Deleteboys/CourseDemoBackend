package de.deleteboys.api;

import de.deleteboys.api.dto.create.CourseCreateDto;
import de.deleteboys.api.dto.create.RatingCreateDto;
import de.deleteboys.api.dto.summary.CourseSummaryDto;
import de.deleteboys.api.dto.summary.RatingSummaryDto;
import de.deleteboys.api.dto.update.CourseUpdateDto;
import de.deleteboys.api.dto.summary.LessonSummaryDto;
import de.deleteboys.domain.Course;
import de.deleteboys.domain.User;
import de.deleteboys.services.CourseService;
import de.deleteboys.services.LessonService;
import de.deleteboys.services.UserService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.stream.Collectors;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoursesResource {

    @Inject
    CourseService courseService;

    @Inject
    LessonService lessonService;

    @Inject
    UserService userService;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @RolesAllowed("user:read")
    public List<CourseSummaryDto> getAllCourses() {
        List<Course> allCoursesFromDb = Course.listAll();

        return allCoursesFromDb.stream()
                .map(CourseSummaryDto::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{courseId}")
    @RolesAllowed("user:read")
    public CourseSummaryDto getCourse(@PathParam("courseId") Long courseId) {
        return courseService.getCourseSummary(courseId);
    }

    @GET
    @Path("/{courseId}/lessons")
    @RolesAllowed("user:read")
    public List<LessonSummaryDto> getLessonsFromCourse(@PathParam("courseId") Long courseId) {
        return courseService.getAllLessonsOfCourse(courseId);
    }

    @GET
    @Path("/{courseId}/lessons/{lessonId}")
    @RolesAllowed("user:read")
    public LessonSummaryDto getLesson(@PathParam("courseId") Long courseId, @PathParam("lessonId") Long lessonId) {
        return lessonService.getLessonSummaryInCourse(courseId, lessonId);
    }

    @GET
    @Path("/{courseId}/lessons/{lessonId}/ratings")
    @RolesAllowed("user:read")
    public List<RatingSummaryDto> getRatingsOfLesson(@PathParam("courseId") Long courseId, @PathParam("lessonId") Long lessonId) {
        return lessonService.getRatingsOfLesson(courseId, lessonId);
    }

    @POST
    @Path("/{courseId}/lessons/{lessonId}/ratings")
    @RolesAllowed("user:read")
    public RatingSummaryDto rateLesson(@PathParam("courseId") Long courseId, @PathParam("lessonId") Long lessonId, @Valid RatingCreateDto rating) {
        String email = securityIdentity.getPrincipal().getName();
        User user = userService.getUserByEmail(email);
        return lessonService.createRating(courseId, lessonId,rating, user);
    }

    //--- Admin endpoints ---

    @PATCH
    @Path("/{courseId}")
    @RolesAllowed("admin:full")
    public CourseSummaryDto patchCourse(@PathParam("courseId") Long courseId, CourseUpdateDto dto) {
        return courseService.patchCourse(courseId, dto);
    }

    @POST
    @RolesAllowed("admin:full")
    public CourseSummaryDto createCourse(@Valid CourseCreateDto courseCreate) {
        return courseService.createCourse(courseCreate);
    }
}
