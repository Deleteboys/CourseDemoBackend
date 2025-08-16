package de.deleteboys.security;

import de.deleteboys.api.dto.PermissionSummeryDto;
import de.deleteboys.api.dto.UserSummaryDto;
import de.deleteboys.domain.Permission;
import de.deleteboys.domain.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    public List<UserSummaryDto> getAllUsers() {
        List<User> allUsersFromDb = User.listAll();
        return allUsersFromDb.stream().map(UserSummaryDto::new).collect(Collectors.toList());
    }

    public List<PermissionSummeryDto> getAllPermissionsFromUser(Long userId) {
        User user = User.findById(userId);
        if (user == null) {
            throw new NotFoundException("Benutzer mit der ID " + userId + " wurde nicht gefunden.");
        }
        return user.permissions.stream().map(PermissionSummeryDto::new).collect(Collectors.toList());
    }

    public List<PermissionSummeryDto> getAllPermissionsFromUser(User user) {
        return user.permissions.stream().map(PermissionSummeryDto::new).collect(Collectors.toList());
    }

    @Transactional
    public List<PermissionSummeryDto> assignPermissionToUser(Long userId, Long permissionId) {
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

}
