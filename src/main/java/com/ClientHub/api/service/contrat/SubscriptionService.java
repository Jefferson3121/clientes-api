package com.ClientHub.api.service.contrat;

import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;

public interface SubscriptionService {

    public SubscriptionResponseDTO createSubscription(SubscriptionRequestDTO subscriptionRequestDTO);

    public void deleteSubscription(Integer id);

    public void activateSubscription(Integer id);

    public void cancelSubscription(Integer id);

    public void renewSubscription(Integer id);


}
