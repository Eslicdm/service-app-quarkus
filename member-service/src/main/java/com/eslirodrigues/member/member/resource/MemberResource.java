package com.eslirodrigues.member.member.resource;

import com.eslirodrigues.member.core.entity.Member;
import com.eslirodrigues.member.member.dto.CreateMemberRequest;
import com.eslirodrigues.member.member.dto.UpdateMemberRequest;
import com.eslirodrigues.member.member.service.MemberService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/api/v1/members")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Member Management", description = "APIs for creating, retrieving, updating, and deleting members")
@SecurityRequirement(name = "bearerAuth")
public class MemberResource {

    private final MemberService memberService;
    private final JsonWebToken jwt;

    MemberResource(MemberService memberService, JsonWebToken jwt) {
        this.memberService = memberService;
        this.jwt = jwt;
    }

    @GET
    @RolesAllowed({"ROLE_manager", "ROLE_admin"})
    @Operation(
            summary = "Get all members for a manager",
            description = "Retrieves a list of all members associated with the authenticated manager"
    )
    @APIResponse(responseCode = "200", description = "Successfully retrieved the list of members")
    @APIResponse(responseCode = "401", description = "Unauthorized - JWT is missing or invalid")
    @APIResponse(responseCode = "403", description = "Forbidden - User does not have 'manager' or 'admin' role")
    public Response getAllMembers() {
        String managerId = jwt.getSubject();
        List<Member> members = memberService.getAllMembersByManagerId(managerId);
        return Response.ok(members).build();
    }

    @GET
    @Path("/{memberId}")
    @RolesAllowed({"ROLE_manager", "ROLE_admin"})
    public Response getMemberById(
            @PathParam("memberId") Long memberId
    ) {
        String managerId = jwt.getSubject();
        Member member = memberService.getMemberById(managerId, memberId);
        return Response.ok(member).build();
    }

    @POST
    @RolesAllowed({"ROLE_manager", "ROLE_admin"})
    public Response createMember(@Valid CreateMemberRequest request) {
        String managerId = jwt.getSubject();
        Member member = memberService.createMember(managerId, request);
        return Response.status(Response.Status.CREATED).entity(member).build();
    }

    @PUT
    @Path("/{memberId}")
    @RolesAllowed({"ROLE_manager", "ROLE_admin"})
    public Response updateMember(
            @PathParam("memberId") Long memberId,
            @Valid UpdateMemberRequest request
    ) {
        Member member = memberService.updateMember(memberId, request);
        return Response.ok(member).build();
    }

    @DELETE
    @Path("/{memberId}")
    @RolesAllowed({"ROLE_manager", "ROLE_admin"})
    public Response deleteMember(
            @PathParam("memberId") Long memberId
    ) {

        memberService.deleteMember(memberId);
        return Response.noContent().build();
    }
}
