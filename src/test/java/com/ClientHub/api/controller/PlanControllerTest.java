package com.ClientHub.api.controller;

import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.exception.PlanNoFoundException;
import com.ClientHub.api.service.contrat.PlanService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebMvcTest(PlanController.class)
public class PlanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PlanService planService;

    @Autowired
    private ObjectMapper objectMapper;



    PLanRequestDTO validRequest = new PLanRequestDTO(
            "Plan Premium",
            new BigDecimal("60000.00"),
            PlanDuration.MONTLY
    );

    PlanResponseDTO validResponse = new PlanResponseDTO(
            1,
            "Plan Premium",
            new BigDecimal("60000.00"),
            State.ACTIVE,
            PlanDuration.MONTLY
    );




    @Nested
    @DisplayName("POST /plan - createPlan")
    class CreatePlanTest {

        @Test
        @DisplayName("Should return 201 and created plan when request is valid")
        public void shouldReturn201AndCreatedPlanWhenRequestIsValid() throws Exception {
            when(planService.createPlan(validRequest)).thenReturn(validResponse);

            mockMvc.perform(post("/plan")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(validRequest))
                            .accept(MediaType.APPLICATION_JSON))
                    .andDo(print())
                    .andExpect(status().isCreated())
                    .andExpect(content().json(objectMapper.writeValueAsString(validResponse)));
        }

        @Test
        @DisplayName("Should return 400 when one or more request fields are null or blank")
        public void shouldReturn400WhenRequestFieldsAreNullOrBlank() throws Exception {
            PLanRequestDTO invalidRequest = new PLanRequestDTO(null, null, null);

            mockMvc.perform(post("/plan")
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
                        "name": "Plan Premium",
                        "price": "not-a-number",
                        "duration": "MONTHLY"
                    }
                    """;

            mockMvc.perform(post("/plan")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(malformedJson)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest());
        }
    }




    @Nested
    @DisplayName("DELETE /plan/{id} - deletePlan")
    class DeletePlanTest {

        @Test
        @DisplayName("Should return 200 when plan is deleted successfully")
        public void shouldReturn200WhenPlanIsDeletedSuccessfully() throws Exception {
            doNothing().when(planService).deletePlan(1);

            mockMvc.perform(delete("/plan/1"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 404 when plan does not exist")
        public void shouldReturn404WhenPlanDoesNotExist() throws Exception {
            doThrow(new PlanNoFoundException("Plan no encontrado"))
                    .when(planService).deletePlan(99);

            mockMvc.perform(delete("/plan/99"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(delete("/plan/abc"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when id is negative")
        public void shouldReturn400WhenIdIsNegative() throws Exception {
            doThrow(new IllegalArgumentException("Id invalido"))
                    .when(planService).deletePlan(-1);

            mockMvc.perform(delete("/plan/-1"))
                    .andExpect(status().isBadRequest());
        }
    }




    @Nested
    @DisplayName("GET /plan/{id} - getPlanById")
    class GetPlanByIdTest {

        @Test
        @DisplayName("Should return 200 and plan DTO when plan exists")
        public void shouldReturn200AndPlanDTOWhenPlanExists() throws Exception {
            when(planService.getPlanById(1)).thenReturn(validResponse);

            mockMvc.perform(get("/plan/1")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(validResponse)));
        }

        @Test
        @DisplayName("Should return 404 when plan does not exist")
        public void shouldReturn404WhenPlanDoesNotExist() throws Exception {
            when(planService.getPlanById(99))
                    .thenThrow(new PlanNoFoundException("Plan no encontrado"));

            mockMvc.perform(get("/plan/99")
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(get("/plan/abc"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when id is negative")
        public void shouldReturn400WhenIdIsNegative() throws Exception {
            when(planService.getPlanById(-1))
                    .thenThrow(new IllegalArgumentException("Id invalido"));

            mockMvc.perform(get("/plan/-1"))
                    .andExpect(status().isBadRequest());
        }
    }




    @Nested
    @DisplayName("PATCH /plan/{id}/price - updatePlanPrice")
    class UpdatePlanPriceTest {

        @Test
        @DisplayName("Should return 200 when price is updated successfully")
        public void shouldReturn200WhenPriceIsUpdatedSuccessfully() throws Exception {
            doNothing().when(planService).updatePlanPrice(1, new BigDecimal("70000.00"));

            mockMvc.perform(patch("/plan/1/price")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("70000.00"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 404 when plan does not exist")
        public void shouldReturn404WhenPlanDoesNotExist() throws Exception {
            doThrow(new PlanNoFoundException("Plan no encontrado"))
                    .when(planService).updatePlanPrice(eq(99), any());

            mockMvc.perform(patch("/plan/99/price")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("70000.00"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 400 when price is zero or negative")
        public void shouldReturn400WhenPriceIsZeroOrNegative() throws Exception {
            doThrow(new IllegalArgumentException("Precio invalido"))
                    .when(planService).updatePlanPrice(eq(1), any());

            mockMvc.perform(patch("/plan/1/price")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("-1000.00"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(patch("/plan/abc/price")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("70000.00"))
                    .andExpect(status().isBadRequest());
        }
    }





    @Nested
    @DisplayName("PATCH /plan/{id}/state - updatePlanState")
    class UpdatePlanStateTest {

        @Test
        @DisplayName("Should return 200 when state is updated successfully")
        public void shouldReturn200WhenStateIsUpdatedSuccessfully() throws Exception {
            doNothing().when(planService).updatePlanState(1);

            mockMvc.perform(patch("/plan/1/state"))
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return 404 when plan does not exist")
        public void shouldReturn404WhenPlanDoesNotExist() throws Exception {
            doThrow(new PlanNoFoundException("Plan no encontrado"))
                    .when(planService).updatePlanState(99);

            mockMvc.perform(patch("/plan/99/state"))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return 400 when id has invalid data type")
        public void shouldReturn400WhenIdHasInvalidDataType() throws Exception {
            mockMvc.perform(patch("/plan/abc/state"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("Should return 400 when id is negative")
        public void shouldReturn400WhenIdIsNegative() throws Exception {
            doThrow(new IllegalArgumentException("Id invalido"))
                    .when(planService).updatePlanState(-1);

            mockMvc.perform(patch("/plan/-1/state"))
                    .andExpect(status().isBadRequest());
        }
    }
}