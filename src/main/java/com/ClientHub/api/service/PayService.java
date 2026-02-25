package com.ClientHub.api.service;

import com.ClientHub.api.dto.request.CreatePayRequestDTO;
import com.ClientHub.api.dto.response.PayResponseDTO;

public interface PayService {

    public PayResponseDTO createPay(CreatePayRequestDTO createPayRequestDTO);

    public PayResponseDTO getPay(Integer id);


}
