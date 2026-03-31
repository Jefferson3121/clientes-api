package com.ClientHub.api.service.impl.subscription;

import com.ClientHub.api.component.SubscriptionMapper;
import com.ClientHub.api.domain.entity.Customer;
import com.ClientHub.api.domain.entity.Plan;
import com.ClientHub.api.domain.entity.Subscription;
import com.ClientHub.api.domain.enums.StateSubscription;
import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import com.ClientHub.api.repository.CustomerRepository;
import com.ClientHub.api.repository.PlanRepository;
import com.ClientHub.api.repository.SubscriptionRepository;
import com.ClientHub.api.service.contrat.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {


    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;
    private final SubscriptionMapper subscriptionMapper;

    @Transactional
    @Override
    public SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO subscriptionRequest){

        Customer customer = customerRepository.findById(subscriptionRequest.customerId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Cliente con id: %d no existe", subscriptionRequest.customerId())));

        Plan plan = planRepository.findById(subscriptionRequest.planId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Plan con id: %d no existe", subscriptionRequest.planId())));


        Subscription subscription = new Subscription(plan, customer);

       Subscription subscriptionResponse = subscriptionRepository.save(subscription);

       return subscriptionMapper.toSubscriptionResponseDTO(subscriptionResponse);
    }


    @Transactional
    @Override
    public void deleteSubscription(Integer id){

        log.info("inicio");


        Subscription subscription = getSUbscriptionOfRepository(id);

        if (subscription.getState() == StateSubscription.ACTIVE) {
            throw new IllegalStateException("No puede eliminar una subscripcion que este en estado ACTIVE");
        }

        subscriptionRepository.delete(subscription);

        log.info("Final");
    }


    @Transactional
    public void activateSubscription(Integer id){

        Subscription subscription = getSUbscriptionOfRepository(id);

        if (!(subscription.getState() == StateSubscription.EXPIRED)) {
            throw new IllegalStateException("La suscripcion no esta expirada, una suscripcion debe estar en estado EXPIREDE para poder ser activada ");
        }

        subscription.activate();
    }


    @Transactional
    @Override
    public void cancelSubscription(Integer id) {
        Subscription subscription = getSUbscriptionOfRepository(id);
        subscription.ensureNotCancelled();
        subscription.cancel();
    }




    @Transactional
    @Override
    public void renewSubscription(Integer id) {

        Subscription subscription = getSUbscriptionOfRepository(id);
        subscription.renew();
    }


    @Transactional(readOnly = true)
    private Subscription getSUbscriptionOfRepository(Integer id) {

        return subscriptionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entidad con id: %d no existe", id)));
    }
}





