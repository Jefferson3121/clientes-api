package com.ClientHub.api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PayResponseDTO(int id, int suscriptionId, BigDecimal valuePay, LocalDate datePay) {
}
