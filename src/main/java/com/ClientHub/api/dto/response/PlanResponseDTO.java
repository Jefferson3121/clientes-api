package com.ClientHub.api.dto.response;

import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.State;

import java.math.BigDecimal;

public record PlanResponseDTO(int id, String name, BigDecimal price, State state, PlanDuration duration) { }
