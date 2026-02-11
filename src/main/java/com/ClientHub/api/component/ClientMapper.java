package com.ClientHub.api.component;

import com.ClientHub.api.domain.Customer;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;
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

    ClientResponseDTO toClientResponseDTO(Customer customer);
}
