package com.ClientHub.api.component;

import com.ClientHub.api.dto.response.PayResponseDTO;
import com.ClientHub.api.domain.entity.Pay;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface PayMapper {

    @Mapping(source = "subscription.id", target = "subscriptionId")
    PayResponseDTO toPayResponseDTO(Pay pay);


}
