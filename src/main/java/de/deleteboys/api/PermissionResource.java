package de.deleteboys.api;

import de.deleteboys.api.dto.summary.PermissionSummaryDto;
import de.deleteboys.domain.Permission;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/permissions")
@Produces(MediaType.APPLICATION_JSON)
public class PermissionResource {

    @GET
    @RolesAllowed("admin:full")
    public List<PermissionSummaryDto> getAllPermissions() {
        List<Permission> allPermissionsFromDb = Permission.listAll();
        return allPermissionsFromDb.stream().map(PermissionSummaryDto::new).toList();
    }

}
