package com.ClientHub.api.dto.response;

import com.ClientHub.api.domain.enums.State;

public record ClientResponseDTO(String name, String email, State state) {
}
