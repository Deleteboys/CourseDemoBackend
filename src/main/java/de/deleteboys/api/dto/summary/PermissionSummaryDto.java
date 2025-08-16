package de.deleteboys.api.dto.summary;

import de.deleteboys.domain.Permission;

public class PermissionSummaryDto {
    public Long id;
    public String name;
    public String description;

    public PermissionSummaryDto(Permission permission) {
        this.id = permission.id;
        this.name = permission.name;
        this.description = permission.description;
    }
}
