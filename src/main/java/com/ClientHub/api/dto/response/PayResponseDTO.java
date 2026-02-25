package com.ClientHub.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
@Schema(
        name = "PayResponse",
        description = "Objeto para exponer datos de un Pago en una susbcripcion de un cliente"
)
public record PayResponseDTO(

        @Schema(
                description = "Id identificador del pago",
                example = "1"
        )
        int id,
        @Schema(
                description = "Id identificador de la subscricion asociada a el pago",
                example = "23"
        )
        int subscriptionId,
        @Schema(
                description = "Valor comercial del pago",
                example = "34000.00"
        )
        BigDecimal valuePay,
        @Schema(
                description = "Fecha con hora, dia y año en que se realizo el pago"
        )
        LocalDate datePay) {
}
