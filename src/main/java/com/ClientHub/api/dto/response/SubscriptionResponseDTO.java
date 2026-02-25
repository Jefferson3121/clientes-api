package com.ClientHub.api.dto.response;

import com.ClientHub.api.domain.enums.StateSubscription;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(
        name = "SubscriptionResponse",
        description = "Representa la información de una suscripción activa o histórica en el sistema"
)
public record SubscriptionResponseDTO(

        @Schema(
                description = "Identificador único de la suscripción",
                example = "15"
        )
        int id,

        @Schema(
                description = "Identificador del cliente asociado a la suscripción",
                example = "3"
        )
        int customerId,

        @Schema(
                description = "Identificador del plan asociado a la suscripción",
                example = "1"
        )
        int planId,

        @Schema(
                description = "Fecha de inicio de la suscripción",
                example = "2026-02-01",
                type = "string",
                format = "date"
        )
        LocalDate dateStart,

        @Schema(
                description = "Fecha de finalización de la suscripción",
                example = "2026-03-01",
                type = "string",
                format = "date"
        )
        LocalDate dateEnd,

        @Schema(
                description = "Estado actual de la suscripción",
                example = "ACTIVE",
                allowableValues = {
                        "ACTIVE",
                        "CANCELLED",
                        "EXPIRED"
                }
        )
        StateSubscription stateSubscription
) {}

