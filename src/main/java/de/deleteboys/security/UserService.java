package de.deleteboys.security;

import de.deleteboys.api.dto.UserCreateDto;
import de.deleteboys.domain.User;
import de.deleteboys.exceptions.ValidationException;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class UserService {

    @Transactional
    public User createUser(UserCreateDto userDto) {
        validateUserDto(userDto);

        User newUser = new User();
        newUser.username = userDto.username;
        newUser.email = userDto.email;
        newUser.passwordHash = BcryptUtil.bcryptHash(userDto.password);

        newUser.persist();
        return newUser;
    }

    private void validateUserDto(UserCreateDto userDto) {
        Map<String, String> errors = new HashMap<>();

        if (userDto.username == null || userDto.username.trim().length() < 3) {
            errors.put("username", "Der Benutzername muss mindestens 3 Zeichen lang sein.");
        }
        if (userDto.password == null || userDto.password.length() < 3) {
            errors.put("password", "Das Passwort muss mindestens 3 Zeichen lang sein.");
        }
        if (User.find("username", userDto.username).firstResult() != null) {
            errors.put("username", "Dieser Benutzername ist bereits vergeben.");
        }
        if (User.find("email", userDto.email).firstResult() != null) {
            errors.put("email", "Diese E-Mail-Adresse ist bereits vergeben.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Validation failed", errors);
        }
    }
}
