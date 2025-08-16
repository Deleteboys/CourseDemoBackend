package de.deleteboys.api;

import de.deleteboys.api.dto.*;
import de.deleteboys.domain.Permission;
import de.deleteboys.domain.User;
import de.deleteboys.exceptions.ValidationException;
import de.deleteboys.security.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
import java.util.stream.Collectors;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    UserService userService;

    @GET
    @RolesAllowed("admin:full")
    public List<UserSummaryDto> getUser() {
        return userService.getAllUsers();
    }

    @GET
    @RolesAllowed("admin:full")
    @Path("/{userId}/permissions")
    public List<PermissionSummeryDto> getPermissions(@PathParam("userId") Long userId) {
        return userService.getAllPermissionsFromUser(userId);
    }

    @POST
    @RolesAllowed("admin:full")
    @Path("/{userId}/permissions")
    public Response assignPermissionToUser(@PathParam("userId") Long userId, AssignPermissionDto assignPermissionDto) {
        List<PermissionSummeryDto> permissions = userService.assignPermissionToUser(userId, assignPermissionDto.permissionId);
        return Response.status(Response.Status.CREATED).entity(permissions).build();
    }

}
