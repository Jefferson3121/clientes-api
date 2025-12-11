package com.ClientHub.api.dto.response;

import com.ClientHub.api.model.StateClient;

public record ClientResponseDTO(String name, String email, StateClient state) {
}
