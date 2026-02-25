package com.ClientHub.api.dto.response;

import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.State;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(
        name = "PlanResponse",
        description = "Expone los datos que represetan a un Plan comercial"
)
public record PlanResponseDTO(

        @Schema(
                description = "Identificador autogenerado único del plan",
                example = "1"
        )
        int id,

        @Schema(
                description = "Nombre comercial del plan",
                example = "Plan Premium"
        )
        String name,

        @Schema(
                description = "Precio del plan en la moneda configurada",
                example = "29.99"
        )
        BigDecimal price,

        @Schema(
                description = "Estado actual del plan",
                example = "ACTIVE",
                allowableValues = {
                        "ACTIVE",
                        "INACTIVE"
                }
        )
        State state,

        @Schema(
                description = "Duración del plan",
                example = "MONTHLY",
                allowableValues = {
                        "MONTLY",
                        "ANNUAL"
                }
        )
        PlanDuration duration
) {}
