package com.ClientHub.api.component;

import com.ClientHub.api.domain.Subscription;
import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
        )
public interface SubscriptionMapper {

    Subscription toSubscription(SubscriptionRequestDTO subscriptionRequestDTO);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "plan.id", target = "planId")
    SubscriptionResponseDTO toSubscriptionResponseDTO(Subscription subscription);
}
