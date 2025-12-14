package com.ClientHub.api.service;

import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.model.Plan;

import java.math.BigDecimal;

public interface ServicePlan {

    public PlanResponseDTO add(PLanRequestDTO pLanRequestDTO);

    public void deletePlan(int id);

    public PlanResponseDTO getPlan(int id);

    public void changePlanName(int id, String newName);

    public void changePlanPrice(int id, BigDecimal newPrice);

    public void modifyPlanState(int id);
}
