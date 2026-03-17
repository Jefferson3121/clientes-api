package com.ClientHub.api.service.impl.customer;

import com.ClientHub.api.component.ClientMapper;
import com.ClientHub.api.domain.entity.Customer;
import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.CustomerResponseDTO;
import com.ClientHub.api.exception.ClientAlreadyExistsException;
import com.ClientHub.api.repository.CustomerRepository;
import com.ClientHub.api.repository.SubscriptionRepository;
import com.ClientHub.api.service.contrat.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

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
    public State activateCustomer(int id){

        Customer customer = getByCostumerId(id);

        if (customer.getState() == State.ACTIVE){
            throw new IllegalStateException("El cliente ya esta activo");
        }

        customer.activate();

        return customer.getState();
    }


    @Transactional
    public State deactivateCustomer(int idCustomer){

        Customer customer = getByCostumerId(idCustomer);

        if (customer.getState() == State.INACTIVE){
            throw new IllegalStateException("El cliente ya esta desactivado");
        }



        if (subscriptionRepository.existsByCustomerId(customer.getId())){
            throw new IllegalStateException("No se puede eliminar el cliente, existen subcripciodes asociadas a el");
        }



        customer.deactivate();

        return customer.getState();

    }




    @Transactional(readOnly = true)
    @Override
    public CustomerResponseDTO getByIdCustomer(int id) {

        Customer customer = getByCostumerId(id);
        return clientMapper.toClientResponseDTO(customer);
    }


    @Transactional
    @Override
    public void updateCustomerName(int id, ClientRequestChangeNameDTO changeNameDTO){

        Customer customer = getByCostumerId(id);

        customer.updateName(changeNameDTO.newName());

    }


    @Transactional
    @Override
    public void updateCostumerEmail(int id, ClientRequestChangeEmailDTO clientRequestChangeEmailDTO){

        Customer customer = getByCostumerId(id);

        if (clientRequestChangeEmailDTO.newEmail().equals(customer.getEmail())){
            throw new IllegalArgumentException("Email no puede ser igual a el email actual");
        }

        customer.updateEmail(clientRequestChangeEmailDTO.newEmail());
        customerRepository.save(customer);
    }



    private Customer getByCostumerId(int id) {

        if(id <= -1) throw new IllegalArgumentException("El id del cliente no puede ser negativo");

        return customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cliente con id: %d, no existe", id)));
    }
}
