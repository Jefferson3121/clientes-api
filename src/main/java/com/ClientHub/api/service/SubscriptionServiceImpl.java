package com.ClientHub.api.service;

import com.ClientHub.api.component.SubscriptionMapper;
import com.ClientHub.api.domain.Client;
import com.ClientHub.api.domain.Plan;
import com.ClientHub.api.domain.Subscription;
import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import com.ClientHub.api.repository.ClientRepository;
import com.ClientHub.api.repository.PlanRepository;
import com.ClientHub.api.repository.SubscriptionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService{


    private final SubscriptionRepository subscriptionRepository;
    private final ClientRepository clientRepository;
    private final PlanRepository planRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO subscriptionRequest){

        Client client = clientRepository.findById(subscriptionRequest.clienteId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cliente con id: %d no existe", subscriptionRequest.clienteId())));

        Plan plan = planRepository.findById(subscriptionRequest.planId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Plan con id: %d no existe", subscriptionRequest.planId())));


        Subscription subscription = new Subscription(plan, client);

       Subscription subscriptionResponse = subscriptionRepository.save(subscription);

       return subscriptionMapper.toSubscriptionResponseDTO(subscriptionResponse);
    }


    @Transactional
    @Override
    public void deleteSubscription(Integer id){

        Subscription subscription = getSUbscriptionOfRepository(id);

        if (!subscription.isActive()) {
            throw new IllegalStateException("No puede eliinar una etidad Subscription que este en estado ACTIVE");
        }

        subscriptionRepository.delete(subscription);
    }


    @Transactional
    public void activateSubscription(Integer id){

        Subscription subscription = getSUbscriptionOfRepository(id);

        if (!subscription.isExpired()) {
            throw new IllegalStateException("La suscripcion no esta expirada, una suscripcion debe estar en estado EXPIREDE para poder ser activada ");
        }

        subscription.activate();
    }


    @Transactional
    @Override
    public void cancelSubscription(Integer id) {
        Subscription subscription = getSUbscriptionOfRepository(id);
        subscription.isTrueToCancel();
        subscription.cancel();
    }




    @Transactional
    @Override
    public void renewSubscription(Integer id) {

        Subscription subscription = getSUbscriptionOfRepository(id);
        subscription.renew();
    }


    private Subscription getSUbscriptionOfRepository(Integer id) {
        
        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entidad con id:%d no existe", id)));
    }
}





