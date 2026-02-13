package com.ClientHub.api.service;

import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;

import java.math.BigDecimal;

public interface PlanService {

    public PlanResponseDTO createPlan(PLanRequestDTO pLanRequestDTO);

    public void deletePlan(int id);

    public PlanResponseDTO getPlanById(int id);

    public void changePlanName(int id, String newName);

    public void updatePlanPrice(int id, BigDecimal newPrice);

    public void updatePlanState(int id);
}
