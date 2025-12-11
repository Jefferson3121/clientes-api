package com.ClientHub.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ClientRequestChangeEmailDTO(@NotNull Integer id, @NotBlank String currentEmail,@NotBlank String newEmail) {
}
