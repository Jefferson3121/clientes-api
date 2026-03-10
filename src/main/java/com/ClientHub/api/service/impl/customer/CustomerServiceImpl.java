package com.ClientHub.api.service.impl;

import com.ClientHub.api.component.ClientMapper;
import com.ClientHub.api.domain.Customer;
import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;
import com.ClientHub.api.exception.ClientAlreadyExistsException;
import com.ClientHub.api.repository.CustomerRepository;
import com.ClientHub.api.repository.SubscriptionRepository;
import com.ClientHub.api.service.contrat.ClientService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements ClientService {

    private final CustomerRepository customerRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ClientMapper clientMapper;

    @Override
    public void registerClient(ClientRequestDTO clientRequestDTO) {

        if (customerRepository.existsByEmail(clientRequestDTO.email())) {
            throw new ClientAlreadyExistsException(
                    String.format("Cliente con el email %s ya existe", clientRequestDTO.email()));
        }

        Customer customer = clientMapper.toClient(clientRequestDTO);

        customerRepository.save(customer);

    }


    @Transactional
    public ClientResponseDTO deactivateCustomer(int idCustomer){

        Customer customer = getCostumerId(idCustomer);

        if (subscriptionRepository.existByCustomer(customer.getId())){


        }

        customer.deactivate();

    }




    @Transactional(readOnly = true)
    @Override
    public ClientResponseDTO getByIdCustomer(Integer id) {

        Customer customer = getCostumerId(id);
        return clientMapper.toClientResponseDTO(customer);
    }


    @Transactional
    @Override
    public void updateCustomerName(int id, ClientRequestChangeNameDTO changeNameDTO){

        Customer customer = getCostumerId(id);

        customer.updateName(changeNameDTO.newName());

    }


    @Transactional
    @Override
    public void updateCostumerEmail(int id, ClientRequestChangeEmailDTO clientRequestChangeEmailDTO){

        Customer customer = getCostumerId(id);

        if (clientRequestChangeEmailDTO.newEmail().equals(customer.getEmail())){
            throw new IllegalArgumentException("Email no puede ser igual a el email actual");
        }

        customer.updateEmail(clientRequestChangeEmailDTO.newEmail());
        customerRepository.save(customer);
    }

    @Transactional
    @Override
    public void updateCostumerState(int id){

        Customer customer = getCostumerId(id);

        customer.updateState();


    }


    @Transactional(readOnly = true)
    private Customer getCostumerId(Integer id) {

        if (id < 0) {
            throw new IllegalArgumentException(String.format("Formato o valor de id incorrecto"));
        }

        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cliente con id: %d, no existe", id)));
    }
}
