package com.ClientHub.api.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.CustomerResponseDTO;
import com.ClientHub.api.exception.ClientAlreadyExistsException;
import com.ClientHub.api.service.contrat.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import jakarta.persistence.EntityNotFoundException;
@WebMvcTest(ClientController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private ObjectMapper objectMapper;


    @Nested
    @DisplayName("POST /client - registerCustomer")
    public class RegisterCustomerTest {

        @Test
        @DisplayName("Should return 201 when customer is registered successfully")
        public void shouldReturn201WhenCustomerIsRegisteredSuccessfully() throws Exception {

            ClientRequestDTO request = new ClientRequestDTO("Juan Daniel", "emaildejuan@gmail.com", "password123");

            mockMvc.perform(post("/client")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Should return 409 when email already exists")
        public void shouldReturn409WhenEmailAlreadyExists() throws Exception {

            ClientRequestDTO request = new ClientRequestDTO("Juan Daniel", "emaildejuan@gmail.com", "password123");

            doThrow(new ClientAlreadyExistsException("Cliente con el email emaildejuan@gmail.com ya existe"))
                    .when(customerService).registerClient(any(ClientRequestDTO.class));

            mockMvc.perform(post("/client")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isConflict());
        }
    }


    @Nested
    @DisplayName("GET /client/{id} - getByIdCustomer")
    public class GetByIdCustomerTest {

        @Test
        @DisplayName("Should return 200 and body when customer exists")
        public void shouldReturn200WhenCustomerExists() throws Exception {

            CustomerResponseDTO responseDTO = new CustomerResponseDTO("Juan Daniel", "emaildejuan@gmail.com");

            when(customerService.getByIdCustomer(12)).thenReturn(responseDTO);

            mockMvc.perform(get("/client/12"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Juan Daniel"))
                    .andExpect(jsonPath("$.email").value("emaildejuan@gmail.com"));
        }

        @Test
        @DisplayName("Should return 404 when customer does not exist")
        public void shouldReturn404WhenCustomerDoesNotExist() throws Exception {

            when(customerService.getByIdCustomer(99))
                    .thenThrow(new EntityNotFoundException("Cliente con id: 99, no existe"));

            mockMvc.perform(get("/client/99"))
                    .andExpect(status().isNotFound());
        }
    }


    @Nested
    @DisplayName("PATCH /client/{id}/name - updateCustomerName")
    public class UpdateCustomerNameTest {

        @Test
        @DisplayName("Should return 200 when customer name is updated successfully")
        public void shouldReturn200WhenCustomerNameIsUpdatedSuccessfully() throws Exception {

            ClientRequestChangeNameDTO request = new ClientRequestChangeNameDTO("Nuevo Nombre");

            mockMvc.perform(patch("/client/12/name")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 404 when customer does not exist")
        public void shouldReturn404WhenCustomerDoesNotExist() throws Exception {

            ClientRequestChangeNameDTO request = new ClientRequestChangeNameDTO("Nuevo Nombre");

            doThrow(new EntityNotFoundException("Cliente con id: 99, no existe"))
                    .when(customerService).updateCustomerName(eq(99), any(ClientRequestChangeNameDTO.class));

            mockMvc.perform(patch("/client/99/name")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound());
        }
    }


    @Nested
    @DisplayName("PATCH /client/{id}/email - updateCustomerEmail")
    public class UpdateCustomerEmailTest {

        @Test
        @DisplayName("Should return 200 when customer email is updated successfully")
        public void shouldReturn200WhenCustomerEmailIsUpdatedSuccessfully() throws Exception {

            ClientRequestChangeEmailDTO request = new ClientRequestChangeEmailDTO("nuevoemail@gmail.com");

            mockMvc.perform(patch("/client/12/email")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 404 when customer does not exist")
        public void shouldReturn404WhenCustomerDoesNotExist() throws Exception {

            ClientRequestChangeEmailDTO request = new ClientRequestChangeEmailDTO("nuevoemail@gmail.com");

            doThrow(new EntityNotFoundException("Cliente con id: 99, no existe"))
                    .when(customerService).updateCostumerEmail(eq(99), any(ClientRequestChangeEmailDTO.class));

            mockMvc.perform(patch("/client/99/email")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound());
        }
    }


    @Nested
    @DisplayName("PATCH /client/{id}/activate - activateCustomer")
    public class ActivateCustomerTest {

        @Test
        @DisplayName("Should return 200 and ACTIVE state when customer is activated")
        public void shouldReturn200WhenCustomerIsActivated() throws Exception {

            when(customerService.activateCustomer(12)).thenReturn(State.ACTIVE);

            mockMvc.perform(patch("/client/12/activate"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value("ACTIVE"));
        }

        @Test
        @DisplayName("Should return 404 when customer does not exist")
        public void shouldReturn404WhenCustomerDoesNotExist() throws Exception {

            when(customerService.activateCustomer(99))
                    .thenThrow(new EntityNotFoundException("Cliente con id: 99, no existe"));

            mockMvc.perform(patch("/client/99/activate"))
                    .andExpect(status().isNotFound());
        }
    }


    @Nested
    @DisplayName("PATCH /client/{id}/deactivate - deactivateCustomer")
    public class DeactivateCustomerTest {

        @Test
        @DisplayName("Should return 200 and INACTIVE state when customer is deactivated")
        public void shouldReturn200WhenCustomerIsDeactivated() throws Exception {

            when(customerService.deactivateCustomer(12)).thenReturn(State.INACTIVE);

            mockMvc.perform(patch("/client/12/deactivate"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").value("INACTIVE"));
        }

        @Test
        @DisplayName("Should return 404 when customer does not exist")
        public void shouldReturn404WhenCustomerDoesNotExist() throws Exception {

            when(customerService.deactivateCustomer(99))
                    .thenThrow(new EntityNotFoundException("Cliente con id: 99, no existe"));

            mockMvc.perform(patch("/client/99/deactivate"))
                    .andExpect(status().isNotFound());
        }
    }
}