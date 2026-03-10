package com.ClientHub.api.component;

import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.domain.entity.Plan;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
         injectionStrategy = InjectionStrategy.CONSTRUCTOR,
         unmappedSourcePolicy = ReportingPolicy.WARN,
         unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface PlanMapper {

    Plan toPlan(PLanRequestDTO pLanRequestDTO);


    PlanResponseDTO toPlanResponseDTO(Plan plan);
}
