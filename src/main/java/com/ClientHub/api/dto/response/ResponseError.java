package com.ClientHub.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(
        name = "ResponseError",
        description = "Objeto que repesenta los datos de error de una peticion"
)
public record ResponseError(
        @Schema(
                description = "Codigo de estado HTTP  de la peticion",
                example = "400"
        )
        int status,
        @Schema(
                description = "Mensaje de error de la peticion"
        )
        String message,
        @Schema(
                description = "Hora y fecha en la que fallo la peticion"
        )
        LocalDateTime timestamp) {
}
