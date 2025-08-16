package de.deleteboys.api;

import de.deleteboys.api.dto.LoginDto;
import de.deleteboys.api.dto.RegisterDto;
import de.deleteboys.api.dto.ValidationErrorResponseDto;
import de.deleteboys.domain.User;
import de.deleteboys.exceptions.ValidationException;
import de.deleteboys.security.AuthService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Map;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthService authService;

    @POST
    @Path("/login")
    public Response login(LoginDto loginDto) {
        String token = authService.login(loginDto);
        return Response.ok(Map.of("token", token)).build();
    }

    @POST
    @Path("/register")
    public Response register(@Valid RegisterDto registerDto) {
        try {
            User user = authService.createUser(registerDto);
            String token = authService.generateJWT(user);

            return Response.status(Response.Status.CREATED).entity(Map.of("token", token)).build();

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
