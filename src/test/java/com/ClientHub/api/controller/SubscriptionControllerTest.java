package com.ClientHub.api.controller;

import com.ClientHub.api.domain.enums.StateSubscription;
import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import com.ClientHub.api.service.contrat.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;



@WebMvcTest(SubscriptionController.class)
public class SubscriptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriptionService subscriptionService;

    @Autowired
    private ObjectMapper objectMapper;


    SubscriptionRequestDTO validRequest = new SubscriptionRequestDTO(3, 1);

    SubscriptionResponseDTO validResponse = new SubscriptionResponseDTO(
            15,
            3,
            1,
            LocalDate.of(2026, Month.FEBRUARY, 1),
            LocalDate.of(2026, Month.MARCH, 1),
            StateSubscription.ACTIVE
    );




    @Nested
    @DisplayName("POST /subscription - createSubscription")
    class CreateSubscriptionTest {

        @Test
        @DisplayName("Should return 201 and created subscription when request is valid")
        public void shouldReturn201AndCreatedSubscriptionWhenRequestIsValid() throws Exception {
            when(subscriptionService.createSubscription(any())).thenReturn(validResponse);

            mockMvc.perform(post("/subscription")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(objectMapper.writeValueAsString(validResponse)));
        }

        @Test
        @DisplayName("Should return 400 when one or more request fields are null")
        public void shouldReturn400WhenRequestFieldsAreNull() throws Exception {
            SubscriptionRequestDTO invalidRequest = new SubscriptionRequestDTO(null, null);

            mockMvc.perform(post("/subscription")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(invalidRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when json has invalid data types")
        public void shouldReturn400WhenJsonHasInvalidDataTypes() throws Exception {
            String malformedJson = """
                    {
                        "customerId": "not-a-number",
                        "planId": "not-a-number"
                    }
                    """;

            mockMvc.perform(post("/subscription")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(malformedJson)
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 404 when customer does not exist")
        public void shouldReturn404WhenCustomerDoesNotExist() throws Exception {
            when(subscriptionService.createSubscription(any()))
                    .thenThrow(new EntityNotFoundException("Cliente no existe"));

            mockMvc.perform(post("/subscription")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 404 when plan does not exist")
        public void shouldReturn404WhenPlanDoesNotExist() throws Exception {
            when(subscriptionService.createSubscription(any()))
                    .thenThrow(new EntityNotFoundException("Plan no existe"));

            mockMvc.perform(post("/subscription")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }




    @Nested
    @DisplayName("DELETE /subscription/{id} - deleteSubscription")
    class DeleteSubscriptionTest {

        @Test
        @DisplayName("Should return 204 when subscription is deleted successfully")
        public void shouldReturn204WhenSubscriptionIsDeletedSuccessfully() throws Exception {
            doNothing().when(subscriptionService).deleteSubscription(1);

            mockMvc.perform(delete("/subscription/1"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Should return 404 when subscription does not exist")
        public void shouldReturn404WhenSubscriptionDoesNotExist() throws Exception {
            doThrow(new EntityNotFoundException("Subscripcion no encontrada"))
                    .when(subscriptionService).deleteSubscription(99);

            mockMvc.perform(delete("/subscription/99"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 409 when subscription state is ACTIVE")
        public void shouldReturn409WhenSubscriptionStateIsActive() throws Exception {
            doThrow(new IllegalStateException("No puede eliminar una subscripcion en estado ACTIVE"))
                    .when(subscriptionService).deleteSubscription(1);

            mockMvc.perform(delete("/subscription/1"))
                    .andExpect(status().isConflict());
        }

        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(delete("/subscription/abc"))
                    .andExpect(status().isBadRequest());
        }
    }



    @Nested
    @DisplayName("PATCH /subscription/{id}/activate - activateSubscription")
    class ActivateSubscriptionTest {

        @Test
        @DisplayName("Should return 204 when subscription is activated successfully")
        public void shouldReturn204WhenSubscriptionIsActivatedSuccessfully() throws Exception {
            doNothing().when(subscriptionService).activateSubscription(1);

            mockMvc.perform(patch("/subscription/1/activate"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Should return 404 when subscription does not exist")
        public void shouldReturn404WhenSubscriptionDoesNotExist() throws Exception {
            doThrow(new EntityNotFoundException("Subscripcion no encontrada"))
                    .when(subscriptionService).activateSubscription(99);

            mockMvc.perform(patch("/subscription/99/activate"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 409 when subscription is not in EXPIRED state")
        public void shouldReturn409WhenSubscriptionIsNotExpired() throws Exception {
            doThrow(new IllegalStateException("La suscripcion no esta expirada"))
                    .when(subscriptionService).activateSubscription(1);

            mockMvc.perform(patch("/subscription/1/activate"))
                    .andExpect(status().isConflict());
        }

        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(patch("/subscription/abc/activate"))
                    .andExpect(status().isBadRequest());
        }
    }



    @Nested
    @DisplayName("PATCH /subscription/{id}/cancel - cancelSubscription")
    class CancelSubscriptionTest {

        @Test
        @DisplayName("Should return 204 when subscription is cancelled successfully")
        public void shouldReturn204WhenSubscriptionIsCancelledSuccessfully() throws Exception {
            doNothing().when(subscriptionService).cancelSubscription(1);

            mockMvc.perform(patch("/subscription/1/cancel"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Should return 404 when subscription does not exist")
        public void shouldReturn404WhenSubscriptionDoesNotExist() throws Exception {
            doThrow(new EntityNotFoundException("Subscripcion no encontrada"))
                    .when(subscriptionService).cancelSubscription(99);

            mockMvc.perform(patch("/subscription/99/cancel"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 409 when subscription is already cancelled")
        public void shouldReturn409WhenSubscriptionIsAlreadyCancelled() throws Exception {
            doThrow(new IllegalStateException("La subscripcion ya esta cancelada"))
                    .when(subscriptionService).cancelSubscription(1);

            mockMvc.perform(patch("/subscription/1/cancel"))
                    .andExpect(status().isConflict());
        }

        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(patch("/subscription/abc/cancel"))
                    .andExpect(status().isBadRequest());
        }
    }




    @Nested
    @DisplayName("PATCH /subscription/{id}/renew - renewSubscription")
    class RenewSubscriptionTest {

        @Test
        @DisplayName("Should return 204 when subscription is renewed successfully")
        public void shouldReturn204WhenSubscriptionIsRenewedSuccessfully() throws Exception {
            doNothing().when(subscriptionService).renewSubscription(1);

            mockMvc.perform(patch("/subscription/1/renew"))
                    .andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Should return 404 when subscription does not exist")
        public void shouldReturn404WhenSubscriptionDoesNotExist() throws Exception {
            doThrow(new EntityNotFoundException("Subscripcion no encontrada"))
                    .when(subscriptionService).renewSubscription(99);

            mockMvc.perform(patch("/subscription/99/renew"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 409 when subscription is already active")
        public void shouldReturn409WhenSubscriptionIsAlreadyActive() throws Exception {
            doThrow(new IllegalStateException("La subscripcion ya esta activa"))
                    .when(subscriptionService).renewSubscription(1);

            mockMvc.perform(patch("/subscription/1/renew"))
                    .andExpect(status().isConflict());
        }

        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(patch("/subscription/abc/renew"))
                    .andExpect(status().isBadRequest());
        }
    }
}