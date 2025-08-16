package de.deleteboys.security;

import de.deleteboys.api.dto.LoginDto;
import de.deleteboys.domain.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import io.vertx.ext.auth.impl.jose.JWT;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotAuthorizedException;

import java.time.Duration;
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

}
