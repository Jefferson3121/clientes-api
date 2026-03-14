package com.ClientHub.api.repository;

import com.ClientHub.api.domain.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Integer> {

    boolean existsByCustomerId(int id);
}
