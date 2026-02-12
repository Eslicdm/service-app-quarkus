package com.eslirodrigues.member.member.dto;

import com.eslirodrigues.member.core.entity.ServiceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Schema(description = "Request to update an existing member")
public record UpdateMemberRequest(

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        String email,

        @JsonProperty("birthDate")
        @Past(message = "Birth date must be in the past")
        LocalDate birthDate,

        @URL(message = "Photo must be a valid URL")
        String photo,

        @JsonProperty("serviceType")
        ServiceType serviceType
) {}
