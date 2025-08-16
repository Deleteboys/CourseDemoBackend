package de.deleteboys.api;

import de.deleteboys.api.dto.create.LessonCreateDto;
import de.deleteboys.api.dto.summary.LessonSummaryDto;
import de.deleteboys.api.dto.update.LessonUpdateDto;
import de.deleteboys.services.LessonService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/lessons")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LessonResource {

    @Inject
    LessonService lessonService;

    @GET
    @RolesAllowed("admin:full")
    public List<LessonSummaryDto> getAllLessons() {
        return lessonService.getAllLessons();
    }

    @PATCH
    @RolesAllowed("admin:full")
    @Path("/{lessonId}")
    public LessonSummaryDto patchLesson(@PathParam("lessonId") Long lessonId, LessonUpdateDto lessonUpdate) {
        return lessonService.patchLesson(lessonId, lessonUpdate);
    }

    @POST
    @RolesAllowed("admin:full")
    public LessonSummaryDto createLesson(@Valid LessonCreateDto lessonCreate) {
        return lessonService.createLesson(lessonCreate);
    }

}
