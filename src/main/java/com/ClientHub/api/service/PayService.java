package com.ClientHub.api.service;

import com.ClientHub.api.dto.request.MakePayRequest;
import com.ClientHub.api.dto.response.PayResponseDTO;

public interface PayService {

    public PayResponseDTO createPay(MakePayRequest makePayRequest);

    public PayResponseDTO getPay(Integer id);


}
