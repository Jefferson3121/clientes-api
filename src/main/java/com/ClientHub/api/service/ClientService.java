package com.ClientHub.api.service;

import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;

public interface ClientService {

    public void registerCliente(ClientRequestDTO clientRequestDTO);

    public ClientResponseDTO getById(Integer id);

    public void updateClientName(int id, String newName);

    public void updateClientEmail(int id, ClientRequestChangeEmailDTO clientRequestChangeEmailDTO);

    public void updateClientState(int id);
}
