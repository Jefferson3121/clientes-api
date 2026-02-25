package com.ClientHub.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
        name = "ClientRequestChangeNameDTO",
        description = "Objeto con los datos necesarios para modificar el nombre de un cliente"
)
public record ClientRequestChangeNameDTO(
        @NotBlank
        @Schema(
                description = "Nuevo nombre para el cliente",
                example = "Juan Perez"
        )
        String newName
) {}
