package com.ClientHub.api.service;

import com.ClientHub.api.component.PlanMapper;
import com.ClientHub.api.domain.Plan;
import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.repository.PlanRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlanImplServiceTest {

    @Mock
    private PlanRepository planRepository;

    @Mock
    private PlanMapper planMapper;

    @InjectMocks
    private PlanImplService planImplService;


    @Test
    public void shouldCallDependenciesWhenAddingPlan(){

        PLanRequestDTO pLanRequestDTO = new PLanRequestDTO("PLan uno", new BigDecimal("4000"), PlanDuration.MONTLY);
        Plan plan = new Plan("PLan 1", new BigDecimal("3000"), PlanDuration.MONTLY);
        PlanResponseDTO planResponseDTO = new PlanResponseDTO(1, "PLan 1", new BigDecimal("994"), State.ACTIVE, PlanDuration.MONTLY);

        when(planMapper.toPlan(pLanRequestDTO)).thenReturn(plan);

        when(planRepository.save(plan)).thenReturn(plan);

        when(planMapper.toPlanResponseDTO(plan)).thenReturn(planResponseDTO);

        PlanResponseDTO planResponseDTO1 = planImplService.add(pLanRequestDTO);

        verify(planRepository, times(1)).save(any(Plan.class));

        verify(planMapper, times(1)).toPlan(any());

        verify(planMapper, times(1)).toPlanResponseDTO(any());
    }














}