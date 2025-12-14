package com.ClientHub.api.service;

import com.ClientHub.api.component.PlanMapper;
import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.model.Plan;
import com.ClientHub.api.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements ServicePlan {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    @Override
    public PlanResponseDTO add(PLanRequestDTO pLanRequestDTO){

        Plan plan = planMapper.toPlan(pLanRequestDTO);

        Plan planResponse = planRepository.save(plan);

        return planMapper.toPlanResponseDTO(planResponse);
    }


}
