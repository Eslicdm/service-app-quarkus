package com.eslirodrigues.member.member.dto;

import com.eslirodrigues.member.core.entity.ServiceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Schema(description = "Request to create a new member")
public record CreateMemberRequest(

        @NotBlank(message = "Name cannot be blank")
        @Schema(description = "Full name of the member")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email should be valid")
        @Schema(description = "Unique email address of the member")
        String email,

        @JsonProperty("birthDate")
        @Schema(description = "Member's date of birth")
        LocalDate birthDate,

        @URL(message = "Photo must be a valid URL")
        @Schema(description = "URL of the member's profile photo")
        String photo,

        @NotNull(message = "Service type cannot be null")
        @JsonProperty("serviceType")
        @Schema(description = "Type of service subscription for the member")
        ServiceType serviceType
) {}
