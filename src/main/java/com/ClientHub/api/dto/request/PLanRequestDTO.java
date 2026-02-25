package com.ClientHub.api.dto.request;

import com.ClientHub.api.domain.enums.PlanDuration;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(
        name = "PlanRequest",
        description = "Objeto para crear un nuevo plan"
)
public record PLanRequestDTO(
        @NotBlank
        @Schema(
                description = "Nombre del plan",
                example = "Plan todo en uno"
        )
        String name,
        @NotNull
        @Schema(
                description = "Precio del plan",
                example = "60000.00"
        )
        BigDecimal
        price,

        @NotNull
        @Schema(
                description = "Duracion de la vigencia del plan comercial",
                example = "MONTLY",
                allowableValues = {
                        "MONTLY",
                        "ANNUAL"
                }
        )
        PlanDuration duration) {
}
