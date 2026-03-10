package com.ClientHub.api.service.contrat;

import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.domain.enums.StateSubscription;
import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.CustomerResponseDTO;

public interface CustomerService {

    public void registerClient(ClientRequestDTO clientRequestDTO);

   public State activateCustomer(int id);

   public State deactivateCustomer(int id);

    public CustomerResponseDTO getByIdCustomer(Integer id);

    public void updateCustomerName(int id, ClientRequestChangeNameDTO clientRequestChangeNameDTO);

    public void updateCostumerEmail(int id, ClientRequestChangeEmailDTO clientRequestChangeEmailDTO);



}
