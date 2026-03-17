package com.ClientHub.api.service.contrat;

import com.ClientHub.api.dto.request.CreatePayRequestDTO;
import com.ClientHub.api.dto.response.PayResponseDTO;

public interface PayService {

    public PayResponseDTO createPay(CreatePayRequestDTO createPayRequestDTO);

    public PayResponseDTO getPay(int id);


}
