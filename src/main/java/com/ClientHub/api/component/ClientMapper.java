package com.ClientHub.api.component;

import com.ClientHub.api.domain.entity.Customer;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.CustomerResponseDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedSourcePolicy = ReportingPolicy.WARN,
        unmappedTargetPolicy = ReportingPolicy.WARN
)
public interface ClientMapper{

    Customer toClient(ClientRequestDTO clientRequestDTO);

    CustomerResponseDTO toClientResponseDTO(Customer customer);
}
