package de.deleteboys.api;

import de.deleteboys.api.dto.*;
import de.deleteboys.api.dto.summary.PermissionSummaryDto;
import de.deleteboys.api.dto.summary.UserSummaryDto;
import de.deleteboys.domain.User;
import de.deleteboys.services.UserService;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    UserService userService;

    @PUT
    @Path("/me/password")
    public Response changePassword(@Valid PasswordChangeDto passwordChangeDto) {
        String email = securityIdentity.getPrincipal().getName();
        userService.changeOwnPassword(email, passwordChangeDto);
        return Response.ok().build();
    }

    @GET
    @RolesAllowed("admin:full")
    public List<UserSummaryDto> getUser() {
        return userService.getAllUsers();
    }

    @GET
    @RolesAllowed("admin:full")
    @Path("/{userId}/permissions")
    public List<PermissionSummaryDto> getPermissions(@PathParam("userId") Long userId) {
        return userService.getAllPermissionsFromUser(userId);
    }

    @POST
    @RolesAllowed("admin:full")
    @Path("/{userId}/permissions")
    public Response assignPermissionToUser(@PathParam("userId") Long userId, AssignPermissionDto assignPermissionDto) {
        List<PermissionSummaryDto> permissions = userService.assignPermissionToUser(userId, assignPermissionDto.permissionId);
        return Response.status(Response.Status.CREATED).entity(permissions).build();
    }

    @PUT
    @RolesAllowed("admin:full")
    @Path("/{userId}/password")
    public Response changePasswordFromUser(@PathParam("userId") Long userId, @Valid PasswordRestDto passwordRestDto) {
        userService.changePassword(userId, passwordRestDto.password);
        return Response.ok().build();
    }

}
