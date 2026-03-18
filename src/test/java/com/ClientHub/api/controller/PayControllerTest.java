package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.CreatePayRequestDTO;
import com.ClientHub.api.dto.response.PayResponseDTO;
import com.ClientHub.api.service.contrat.PayService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;


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

            )
        }

    }
}