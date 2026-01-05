package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import com.ClientHub.api.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@Valid SubscriptionRequestDTO subscriptionRequestDTO){
        SubscriptionResponseDTO subscriptionResponse = subscriptionService.createSubscription(subscriptionRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Integer id) {

        subscriptionService.deleteSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateSubscription(@PathVariable Integer id){

        subscriptionService.activateSubscription(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelSubscription(@PathVariable Integer id) {

        subscriptionService.cancelSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    public ResponseEntity<Void> renewSubscription(@PathVariable Integer id) {

        subscriptionService.renewSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).build();
}
