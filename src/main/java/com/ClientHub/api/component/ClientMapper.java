package com.ClientHub.api.component;

import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;
import com.ClientHub.api.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper{

    Client toClient(ClientRequestDTO clientRequestDTO);

    ClientResponseDTO toClientResponseDTO(Client client);

}
