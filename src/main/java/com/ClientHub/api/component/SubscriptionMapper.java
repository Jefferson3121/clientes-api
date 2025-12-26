package com.ClientHub.api.component;

import com.ClientHub.api.domain.Subscription;
import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spting",
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
        )
public interface SubscriptionMapper {

    Subscription toSubscription(SubscriptionRequestDTO subscriptionRequestDTO);

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "plan.id", target = "planId")
    SubscriptionResponseDTO toSubscriptionResponseDTO(Subscription subscription);
}
