package com.ClientHub.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClientRequestDTO(@NotBlank String name, @NotBlank String email, @NotBlank String password) {
}
