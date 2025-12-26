package com.ClientHub.api.component;

import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;
import com.ClientHub.api.domain.Client;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;


@Mapper(
        componentModel = "spring",
         injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ClientMapper{

    Client toClient(ClientRequestDTO clientRequestDTO);

    ClientResponseDTO toClientResponseDTO(Client client);

}
