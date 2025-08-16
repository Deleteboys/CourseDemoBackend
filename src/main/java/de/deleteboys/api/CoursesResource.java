package de.deleteboys.api;

import de.deleteboys.api.dto.CourseSummaryDto;
import de.deleteboys.domain.Course;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.stream.Collectors;

@Path("/courses")
@Produces(MediaType.APPLICATION_JSON)
public class CoursesResource {

    @GET
    @RolesAllowed("user:read")
    public List<CourseSummaryDto> getAllCourses() {
        List<Course> allCoursesFromDb = Course.listAll();

        return allCoursesFromDb.stream()
                .map(CourseSummaryDto::new)
                .collect(Collectors.toList());
    }
}
