package com.ClientHub.api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MakePayRequest(@NotNull Integer susbriptionId, @NotNull BigDecimal valuePay) {
}
