package com.ClientHub.api.service;

import com.ClientHub.api.component.PayMapper;
import com.ClientHub.api.domain.Pay;
import com.ClientHub.api.domain.Subscription;
import com.ClientHub.api.dto.request.CreatePayRequestDTO;
import com.ClientHub.api.dto.response.PayResponseDTO;
import com.ClientHub.api.repository.PayRepository;
import com.ClientHub.api.repository.SubscriptionRepository;
import com.ClientHub.api.service.contrat.PayService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PayServiceImpl implements PayService {

    private final PayRepository payRepository;
    private final SubscriptionRepository susbcriptionRepsitory;
    private final PayMapper payMapper;

    @Override
    public PayResponseDTO createPay(CreatePayRequestDTO createPayRequestDTO){

        Subscription subscription = susbcriptionRepsitory.findById(createPayRequestDTO.susbriptionId())
                .orElseThrow(() -> new EntityNotFoundException("Entidad no encontrada"));

        Pay pay = Pay.create(subscription, createPayRequestDTO.valuePay());

        Pay payResponse = payRepository.save(pay);

        return payMapper.toPayResponseDTO(payResponse);
    }


    @Override
    public PayResponseDTO getPay(Integer id){
        Objects.requireNonNull(id, "Id del pago no puede ser null");

        if(id <= 0){
            throw new IllegalArgumentException("Id no puede ser menor o igual a cero (0)");
        }

        Pay pay = payRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Pay con id " + id + " no existe"));

        return payMapper.toPayResponseDTO(pay);
    }

}
