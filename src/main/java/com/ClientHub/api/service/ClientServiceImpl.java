package com.ClientHub.api.service;

import com.ClientHub.api.component.ClientMapper;
import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;
import com.ClientHub.api.exception.ClientAlreadyExistsException;
import com.ClientHub.api.exception.ClientNotFoundException;
import com.ClientHub.api.model.Client;
import com.ClientHub.api.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public void registerCliente(ClientRequestDTO clientRequestDTO) {

        if (clientRepository.existsByEmail(clientRequestDTO.email())) {
            throw new ClientAlreadyExistsException(
                    String.format("Cliente con el email %s ya existe", clientRequestDTO.email()));
        }

        Client client = clientMapper.toClient(clientRequestDTO);

        clientRepository.save(client);

    }


    @Transactional(readOnly = true)
    @Override
    public ClientResponseDTO getByIdClient(Integer id) {

        Client client = getClientId(id);
        return clientMapper.toClientResponseDTO(client);
    }


    @Transactional
    @Override
    public void updateClientName(int id, ClientRequestChangeNameDTO changeNameDTO){

        Client client = getClientId(id);

        client.updateName(changeNameDTO.newEmail());

    }


    @Transactional
    @Override
    public void updateClientEmail(int id, ClientRequestChangeEmailDTO clientRequestChangeEmailDTO){

        Client client = getClientId(id);

        if (clientRequestChangeEmailDTO.currentEmail().equals(client.getEmail())){
            throw new IllegalArgumentException("Email no puede ser igual a el email actual");
        }

        client.updateEmail(clientRequestChangeEmailDTO.newEmail());
        clientRepository.save(client);
    }

    @Transactional
    @Override
    public void updateClientState(int id){

        Client client = getClientId(id);

        client.updateState();


    }










    private Client getClientId(Integer id) {

        if (id < 0){
            throw new IllegalArgumentException(String.format("Formato de id incorrecto"));
        }

        return clientRepository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(String.format("Cliente con id: %d, no existe", id)));

    }
}
