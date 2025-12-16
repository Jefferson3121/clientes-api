package com.ClientHub.api.service;

import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;

import java.math.BigDecimal;

public interface PlanService {

    public PlanResponseDTO add(PLanRequestDTO pLanRequestDTO);

    public void delete(int id);

    public PlanResponseDTO getById(int id);

    public void changePlanName(int id, String newName);

    public void changePlanPrice(int id, BigDecimal newPrice);

    public void modifyPlanState(int id);
}
