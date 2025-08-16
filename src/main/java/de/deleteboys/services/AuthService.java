package de.deleteboys.services;

import de.deleteboys.api.dto.LoginDto;
import de.deleteboys.api.dto.RegisterDto;
import de.deleteboys.domain.Permission;
import de.deleteboys.domain.User;
import de.deleteboys.exceptions.ValidationException;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class AuthService {

    public String login(LoginDto loginDto) {
        User user = User.find("username", loginDto.username).firstResult();
        if(user == null) {
            throw new NotAuthorizedException("Invalid username or password");
        }

        if(!BcryptUtil.matches(loginDto.password, user.passwordHash)) {
            throw new NotAuthorizedException("Invalid username or password");
        }

        String token = generateJWT(user);

        return token;
    }

    public String generateJWT(User user) {
        Set<String> roles = user.permissions.stream().map(p -> p.name).collect(Collectors.toSet());

        String token = Jwt.issuer("course-backend").subject(user.username).upn(user.email).groups(roles).expiresIn(Duration.ofHours(24)).sign();

        return token;
    }

    @Transactional
    public User createUser(RegisterDto userDto) {
        validateUserDto(userDto);

        User newUser = new User();
        newUser.username = userDto.username;
        newUser.email = userDto.email;
        newUser.passwordHash = BcryptUtil.bcryptHash(userDto.password);
        newUser.permissions.add(Permission.findById(11));

        newUser.persist();
        return newUser;
    }

    private void validateUserDto(RegisterDto userDto) {
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
