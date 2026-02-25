package com.ClientHub.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(
        name = "CreatePayRequesDTO",
        description = "Objeto para crear un pago de una susbcripcion de un cliente"
)

public record CreatePayRequestDTO(
        @NotNull
        @Schema(
                description = "Identificador unico de la susbcripcion a la que esta asociado el pago",
                example = "23"
        )
        Integer susbriptionId,

        @NotNull
        @Schema(
                description = "Valor del pago de un cliente a una susbcripcion",
                example = "20000.00"
        )
        BigDecimal valuePay) {
}
