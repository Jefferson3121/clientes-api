package com.ClientHub.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "ClientRequestChangeEmail",
        description = "Objeto con datos necesarios para modificar el email de un cliente"
)
public record ClientRequestChangeEmailDTO(
        @NotNull
        @NotBlank
        @Schema(
                description = "Nuevo email para el cliente",
                example = "nuevoemailjuanperez@gmail.com"
        )
        @Email String newEmail) {
}
