package de.deleteboys.api.dto;

import de.deleteboys.domain.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserSummaryDto {

    public Long id;
    public String username;
    public String email;
    public List<String> role;
    public boolean activated;
    public LocalDateTime createdAt;

    public UserSummaryDto(User user) {
        this.id = user.id;
        this.username = user.username;
        this.email = user.email;
        this.role = user.permissions.stream()
                .map(permission -> permission.name)
                .collect(Collectors.toList());;
        this.activated = user.activated;
        this.createdAt = user.createdAt;
    }
}
