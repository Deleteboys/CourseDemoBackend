package de.deleteboys.services;

import de.deleteboys.api.dto.PasswordChangeDto;
import de.deleteboys.api.dto.summary.PermissionSummaryDto;
import de.deleteboys.api.dto.summary.UserSummaryDto;
import de.deleteboys.domain.Permission;
import de.deleteboys.domain.User;
import de.deleteboys.security.JWTHelper;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject
    JWTHelper jwtHelper;

    public List<UserSummaryDto> getAllUsers() {
        List<User> allUsersFromDb = User.listAll();
        return allUsersFromDb.stream().map(UserSummaryDto::new).collect(Collectors.toList());
    }

    public User getUser(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("Benutzer nicht gefunden");
        }
        return user;
    }

    public User getUser(String username) {
        User user = User.find("username", username).firstResult();
        if (user == null) {
            throw new NotFoundException("Benutzer nicht gefunden");
        }
        return user;
    }

    public User getUserOrNull(String username) {
        return User.find("username", username).firstResult();
    }

    public User getUserByEmail(String email) {
        User user = User.find("email", email).firstResult();
        if (user == null) {
            throw new NotFoundException("Benutzer nicht gefunden");
        }
        return user;
    }

    public List<PermissionSummaryDto> getAllPermissionsFromUser(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("Benutzer mit der ID " + userId + " wurde nicht gefunden.");
        }
        return user.permissions.stream().map(PermissionSummaryDto::new).collect(Collectors.toList());
    }

    public List<PermissionSummaryDto> getAllPermissionsFromUser(User user) {
        return user.permissions.stream().map(PermissionSummaryDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<PermissionSummaryDto> assignPermissionToUser(Long userId, Long permissionId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("Benutzer mit der ID " + userId + " wurde nicht gefunden.");
        }
        Permission permission = Permission.findById(permissionId);
        if (permission == null) {
            throw new NotFoundException("Berechtigung mit der ID " + permissionId + " wurde nicht gefunden.");
        }

        user.permissions.add(permission);
        user.persist();

        return getAllPermissionsFromUser(user);
    }

    @Transactional
    public void changeOwnPassword(String email, PasswordChangeDto passwordChangeDto) {
        User user = getUserByEmail(email);
        if(!jwtHelper.validatePassword(passwordChangeDto.currentPassword, user.passwordHash)) {
            throw new NotAuthorizedException("Das alte Passwort ist falsch.");
        }
        changePassword(user.id, passwordChangeDto.newPassword);
    }

    @Transactional
    public void changePassword(Long userId, String password) {
        User user = getUser(userId);
        user.passwordHash = BcryptUtil.bcryptHash(password);
    }

}
