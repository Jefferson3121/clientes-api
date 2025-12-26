package com.ClientHub.api.dto.response;

import java.math.BigDecimal;

public record PlanResponseDTO(int id, String name, BigDecimal price, StatePlan state) { }
