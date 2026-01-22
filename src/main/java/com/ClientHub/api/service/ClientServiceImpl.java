package com.ClientHub.api.service;

import com.ClientHub.api.component.ClientMapper;
import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;
import com.ClientHub.api.exception.ClientAlreadyExistsException;
import com.ClientHub.api.domain.Costumer;
import com.ClientHub.api.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public void registerClient(ClientRequestDTO clientRequestDTO) {

        if (clientRepository.existsByEmail(clientRequestDTO.email())) {
            throw new ClientAlreadyExistsException(
                    String.format("Cliente con el email %s ya existe", clientRequestDTO.email()));
        }

        Costumer costumer = clientMapper.toClient(clientRequestDTO);

        clientRepository.save(costumer);

    }


    @Transactional(readOnly = true)
    @Override
    public ClientResponseDTO getByIdClient(Integer id) {

        Costumer costumer = getClientId(id);
        return clientMapper.toClientResponseDTO(costumer);
    }


    @Transactional
    @Override
    public void updateClientName(int id, ClientRequestChangeNameDTO changeNameDTO){

        Costumer costumer = getClientId(id);

        costumer.updateName(changeNameDTO.newName());

    }


    @Transactional
    @Override
    public void updateClientEmail(int id, ClientRequestChangeEmailDTO clientRequestChangeEmailDTO){

        Costumer costumer = getClientId(id);

        if (clientRequestChangeEmailDTO.newEmail().equals(costumer.getEmail())){
            throw new IllegalArgumentException("Email no puede ser igual a el email actual");
        }

        costumer.updateEmail(clientRequestChangeEmailDTO.newEmail());
        clientRepository.save(costumer);
    }

    @Transactional
    @Override
    public void updateClientState(int id){

        Costumer costumer = getClientId(id);

        costumer.updateState();


    }





    private Costumer getClientId(Integer id) {

        if (id < 0){
            throw new IllegalArgumentException(String.format("Formato o valor de id incorrecto"));
        }

        return clientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cliente con id: %d, no existe", id)));
    }
}
