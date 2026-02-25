package com.ClientHub.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "SubscriptionRequest",
        description = "Objeto con los datos necesarios para crear un objeto de tipo Subscription"
)
public record SubscriptionRequestDTO(
        @NotNull
        @Schema(
                description = "Identificador unico del cliente asociado a la susbcripcion",
                example = "34"
        )
        Integer customerId,
        @NotNull
        @Schema(
                description = "Identificador unico del plan asociado a la susbripcion",
                example = "34"
        )
        Integer planId) {
}
