package com.ClientHub.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ClientRequestChangeNameDTO(@NotBlank String newEmail) {
}
