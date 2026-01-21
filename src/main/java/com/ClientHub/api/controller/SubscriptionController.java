package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import com.ClientHub.api.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@Valid @RequestBody SubscriptionRequestDTO subscriptionRequestDTO){
        SubscriptionResponseDTO subscriptionResponse = subscriptionService.createSubscription(subscriptionRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Integer id) {

        validateId(id);

        subscriptionService.deleteSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateSubscription(@PathVariable Integer id){

        validateId(id);

        subscriptionService.activateSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelSubscription(@PathVariable Integer id) {

        validateId(id);

        subscriptionService.cancelSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    public ResponseEntity<Void> renewSubscription(@PathVariable Integer id) {

        validateId(id);

        subscriptionService.renewSubscription(id);
        return ResponseEntity.status(HttpStatus.OK).build();

    }


    private void validateId(Integer id){
        Objects.requireNonNull(null, "Id no puede ser null");

        if (id <= 0){
            throw new IllegalArgumentException(String.format("(Id = %d) id no puede ser igual o menor a cero", id));
        }
    }
}
