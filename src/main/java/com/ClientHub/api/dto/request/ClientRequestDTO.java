package com.ClientHub.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "ClientRequest",
        description = "Objeto para crear y registrar un nuevo cliente"
)

public record ClientRequestDTO(

        @NotBlank
        @Schema(
                description = "Nombre del cliente",
                example = "Maria Juanita Garcia Rodriguez"
        )
        String name,

        @NotBlank
        @Schema(
                description = "Email del cliente,",
                example = "mariagarcia10@gmail.com"
        )
        String email,

        @NotBlank
        @Schema(
                description = "Contraseña con la que se guardara el cliente",
                example = "contraseñademaria"
        )
        String password
) {}
