package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.CreatePayRequestDTO;
import com.ClientHub.api.dto.response.PayResponseDTO;
import com.ClientHub.api.service.contrat.PayService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;



import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Mockito.when;


@WebMvcTest(PayController.class)
class PayControllerTest {

    @Autowired
    private MockMvc  mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PayService payService;



    @Nested
    @DisplayName("POST   /pay - createPay")
    public class PayCreateTest{


        @Test
        @DisplayName("create pay and return 201 when request is valid")
        public void shouldReturn201WhenRequestisValid() throws Exception{

            CreatePayRequestDTO payRequest = new CreatePayRequestDTO(34, new BigDecimal("50000"));

            PayResponseDTO payResponse = new PayResponseDTO(12, 34, new BigDecimal("50000"), LocalDate.of(2026, Month.JANUARY, 12));


            when(payService.createPay(payRequest)).thenReturn(payResponse);

            mockMvc.perform(post("/pay")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json(objectMapper.writeValueAsString(payResponse)));
        }




        @Test
        @DisplayName("Deberia lretornar 400 si uno o mas de los campos del request son null")
        public void deberiaRetornar400CuandoReuestTieneDatosNull()throws Exception {

           CreatePayRequestDTO payRequestDTO = new CreatePayRequestDTO(null, null);


            mockMvc.perform(post("/pay")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payRequestDTO))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        }



        @Test
        @DisplayName("Should return 400 when one or more request fields are null")
        public void shouldReturn400WhenRequestFieldsAreNull() throws Exception {

            String payRequest = """
                    
                    {
                    "subscriptionId": "id",
                    "valuePay": 12000
                    }
                    """;

            mockMvc.perform(post("/pay")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payRequest)
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());

        }


        @Test
        @DisplayName("Should return 400 when any field has an invalid data type")
        public void shouldReturn400WhenFieldsHaveInvalidDataType() throws Exception {

            CreatePayRequestDTO payRequestDTO = new CreatePayRequestDTO(12, new BigDecimal("50000"));


            when(payService.createPay(payRequestDTO)).thenThrow(new EntityNotFoundException("Susbcription con id proporcionado no existe"));

            mockMvc.perform(post("/pay")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payRequestDTO))
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("GET  /pay   getPay")
    class getPayTest{


        @Test
        @DisplayName("Should return 200 and pay DTO when pay id exists")
        public void shouldReturn200WithPayDTOWhenPayIdExists() throws Exception {

            PayResponseDTO payResponse = new PayResponseDTO(12, 34, new BigDecimal("40000"), LocalDate.of(2026, Month.FEBRUARY, 2));

            when(payService.getPay(12)).thenReturn(payResponse);

            mockMvc.perform(get("/pay/12")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(payResponse)));
        }


        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(get("/pay/idIncorrecto"))
                    .andExpect(status().isBadRequest());
        }


        @Test
        @DisplayName("Should return 400 when id is negative")
        public void shouldReturn400WhenIdIsNegative() throws Exception {
            when(payService.getPay(-12))
                    .thenThrow(new IllegalArgumentException("Id no puede ser menor o igual a cero (0)"));

            mockMvc.perform(get("/pay/-12"))
                    .andExpect(status().isBadRequest());
        }
    }
}