package de.deleteboys.api;

import de.deleteboys.api.dto.UserCreateDto;
import de.deleteboys.api.dto.ValidationErrorResponseDto;
import de.deleteboys.domain.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @POST
    @Transactional
    public Response createUser(UserCreateDto userDto) {

        Map<String, String> errors = new HashMap<>();

        if (userDto.username == null || userDto.username.trim().length() < 3) {
            errors.put("username", "Der Benutzername muss mindestens 3 Zeichen lang sein.");
        }
        if (userDto.password == null || userDto.password.length() < 3) {
            errors.put("password", "Das Passwort muss mindestens 8 Zeichen lang sein.");
        }
        if (userDto.email == null || !userDto.email.contains("@")) {
            errors.put("email", "Die E-Mail-Adresse ist ungültig.");
        }

        if (User.find("username", userDto.username).firstResult() != null) {
            errors.put("username", "Dieser Benutzername ist bereits vergeben.");
        }
        if (User.find("email", userDto.email).firstResult() != null) {
            errors.put("email", "Diese E-Mail-Adresse ist bereits vergeben.");
        }

        if (!errors.isEmpty()) {
            ValidationErrorResponseDto errorResponse = new ValidationErrorResponseDto(
                    "Die Registrierung ist fehlgeschlagen. Bitte überprüfe deine Eingaben.",
                    errors
            );
            return Response.status(Response.Status.BAD_REQUEST).entity(errorResponse).build();
        }

        User newUser = new User();

        newUser.username = userDto.username;
        newUser.email = userDto.email;
        newUser.passwordHash = BcryptUtil.bcryptHash(userDto.password);
        newUser.activated = true;
        newUser.persist();

        return Response.status(Response.Status.CREATED).entity(userDto).build();
    }

}
