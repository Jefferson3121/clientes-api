package com.ClientHub.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubscriptionRequestDTO(@NotNull Integer clienteId, @NotNull Integer planId) {
}
