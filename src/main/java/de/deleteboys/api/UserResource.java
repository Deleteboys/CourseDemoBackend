package de.deleteboys.api;

import de.deleteboys.api.dto.UserCreateDto;
import de.deleteboys.api.dto.ValidationErrorResponseDto;
import de.deleteboys.exceptions.ValidationException;
import de.deleteboys.security.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @POST
    public Response createUser(UserCreateDto userDto) {
        try {
            userService.createUser(userDto);

            return Response.status(Response.Status.CREATED).entity(userDto).build();

        } catch (ValidationException e) {
            ValidationErrorResponseDto errorResponse = new ValidationErrorResponseDto(
                    "Die Eingabe ist ungültig. Bitte korrigiere die Fehler.",
                    e.getErrors()
            );

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        }
    }

}
