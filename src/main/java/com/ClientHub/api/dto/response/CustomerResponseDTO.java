package com.ClientHub.api.dto.response;

import com.ClientHub.api.domain.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        name = "ClientResponse",
        description = "Objeto que exponer los datos de un cliente"
)

public record ClientResponseDTO(
        @Schema(
                description = "Nombre del cliente",
                example = "Juan Perez"
        )
        String name,
        @Schema(
                description = "Email unico por cliente",
                example = "juanperez34@gmail.com"
        )
        String email
        ) {
}
