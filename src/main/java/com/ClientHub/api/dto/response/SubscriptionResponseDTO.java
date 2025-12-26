package com.ClientHub.api.dto.response;

import com.ClientHub.api.domain.enums.StateSubscription;

import java.time.LocalDate;

public record SubscriptionResponseDTO(int id, int clienteId, int planId, LocalDate dateStartm, LocalDate dateEnd, StateSubscription stateSubscription) {
}
