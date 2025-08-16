package de.deleteboys.services;

import de.deleteboys.domain.User;
import de.deleteboys.domain.log.LoginLog;
import de.deleteboys.domain.log.UpdateLog;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class LogService {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UserService userService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void logLoginAttempt(String username, String ip, String userAgent, boolean success) {

        User user = userService.getUserOrNull(username);

        LoginLog log = new LoginLog();
        log.user = user;
        log.ipAddress = ip;
        log.userAgent = userAgent;
        log.success = success;
        log.persist();
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void logEntityUpdate(String tableName, Long entityId, String fieldName, String oldValue, String newValue) {
        String email = securityIdentity.getPrincipal().getName();
        User performingUser = userService.getUserByEmail(email);

        UpdateLog log = new UpdateLog();
        log.user = performingUser;
        log.tableName = tableName;
        log.fieldName = fieldName;
        log.entityId = entityId;
        log.oldValue = oldValue;
        log.newValue = newValue;

        log.persist();
    }

}
