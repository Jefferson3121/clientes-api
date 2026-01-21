package com.ClientHub.api.component;

import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.domain.Plan;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring",
         injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PlanMapper {

    Plan toPlan(PLanRequestDTO pLanRequestDTO);


    PlanResponseDTO toPlanResponseDTO(Plan plan);
}
