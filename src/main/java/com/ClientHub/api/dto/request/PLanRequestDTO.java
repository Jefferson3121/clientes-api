package com.ClientHub.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PLanRequestDTO(@NotBlank String name,@NotNull BigDecimal price) {
}
