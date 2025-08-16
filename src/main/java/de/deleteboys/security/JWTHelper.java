package de.deleteboys.security;

import de.deleteboys.domain.User;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class JWTHelper {

    public String generateJWT(User user) {
        Set<String> roles = user.permissions.stream().map(p -> p.name).collect(Collectors.toSet());

        String token = Jwt.issuer("course-backend").subject(user.username).upn(user.email).groups(roles).expiresIn(Duration.ofHours(24)).sign();

        return token;
    }

    public boolean validatePassword(String password, String hashedPassword) {
        return BcryptUtil.matches(password, hashedPassword);
    }
}
