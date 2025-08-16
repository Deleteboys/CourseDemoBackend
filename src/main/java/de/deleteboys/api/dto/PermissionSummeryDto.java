package de.deleteboys.api.dto;

import de.deleteboys.domain.Permission;

public class PermissionSummeryDto {
    public Long id;
    public String name;
    public String description;

    public PermissionSummeryDto(Permission permission) {
        this.id = permission.id;
        this.name = permission.name;
        this.description = permission.description;
    }
}
