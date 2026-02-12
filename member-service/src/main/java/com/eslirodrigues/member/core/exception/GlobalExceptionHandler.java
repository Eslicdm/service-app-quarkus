package com.eslirodrigues.member.core.exception;

import io.quarkus.security.ForbiddenException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

public class GlobalExceptionHandler {

    @ServerExceptionMapper
    public Response handleEntityNotFoundException(EntityNotFoundException e) {
        return buildResponse(Response.Status.NOT_FOUND, "Member Not Found", e.getMessage());
    }

    @ServerExceptionMapper
    public Response handleDuplicateEmailException(DuplicateEmailException e) {
        return buildResponse(Response.Status.CONFLICT, "Resource Conflict", e.getMessage());
    }

    @ServerExceptionMapper
    public Response handleForbiddenException(ForbiddenException e) {
        return buildResponse(Response.Status.FORBIDDEN, "Access Denied", e.getMessage());
    }

    private Response buildResponse(Response.Status status, String title, String detail) {
        return Response.status(status)
                .entity(new ErrorResponse(title, detail, status.getStatusCode()))
                .build();
    }

    public record ErrorResponse(String title, String detail, int status) {}
}