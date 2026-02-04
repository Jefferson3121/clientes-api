package com.ClientHub.api.service;

import com.ClientHub.api.component.PlanMapper;
import com.ClientHub.api.domain.Plan;
import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.exception.PlanNoFoundException;
import com.ClientHub.api.exception.UnchangedValueException;
import com.ClientHub.api.repository.PlanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PlanImplServiceTest {

    @Mock
    private PlanRepository planRepository;

    @Mock
    private PlanMapper planMapper;

    @InjectMocks
    private PlanImplService planImplService;

    @Nested
    @DisplayName("add()")
    class AddTests {

        @Test
        @DisplayName("should call mapper and repository once")
        void shouldCallDependenciesWhenAddingPlan() {

            PLanRequestDTO requestDTO =
                    new PLanRequestDTO("PLan uno",
                            new BigDecimal("4000"),
                            PlanDuration.MONTLY);

            Plan plan =
                    new Plan("PLan 1",
                            new BigDecimal("3000"),
                            PlanDuration.MONTLY);

            PlanResponseDTO responseDTO =
                    new PlanResponseDTO(1,
                            "PLan 1",
                            new BigDecimal("994"),
                            State.ACTIVE,
                            PlanDuration.MONTLY);

            when(planMapper.toPlan(requestDTO)).thenReturn(plan);
            when(planRepository.save(plan)).thenReturn(plan);
            when(planMapper.toPlanResponseDTO(plan)).thenReturn(responseDTO);

            planImplService.add(requestDTO);

            verify(planMapper, times(1)).toPlan(any());
            verify(planRepository, times(1)).save(any(Plan.class));
            verify(planMapper, times(1)).toPlanResponseDTO(any());
        }

        @Test
        @DisplayName("should create plan successfully")
        void shouldCreatePlanSuccessfully() {

            PLanRequestDTO requestDTO =
                    new PLanRequestDTO("Plan Amigos",
                            new BigDecimal("40000"),
                            PlanDuration.MONTLY);

            Plan plan =
                    new Plan("Plan Amigos",
                            new BigDecimal("40000"),
                            PlanDuration.MONTLY);

            PlanResponseDTO responseDTO =
                    new PlanResponseDTO(1,
                            "Plan Amigos",
                            new BigDecimal("40000"),
                            State.ACTIVE,
                            PlanDuration.MONTLY);

            when(planMapper.toPlan(any(PLanRequestDTO.class)))
                    .thenReturn(plan);

            when(planRepository.save(any(Plan.class)))
                    .thenReturn(plan);

            when(planMapper.toPlanResponseDTO(any()))
                    .thenReturn(responseDTO);

            PlanResponseDTO result = planImplService.add(requestDTO);

            assertThat(result).isNotNull();
            assertThat(result.id()).isEqualTo(1L);
            assertThat(result.name()).isEqualTo("Plan Amigos");
            assertThat(result.price()).isEqualByComparingTo("40000");
        }
    }


    @Nested
    @DisplayName("delete")
    class deleteTest{



        @Test
        @DisplayName("should throw an exception if the ID is invalid when deleting a plan.")
        public void ThrowExceptionIfIdIsInvalid(){

            assertThrows(IllegalArgumentException.class, () -> {
                planImplService.delete(-1);
            });

            verifyNoInteractions(planRepository);
        }


        @Test
        @DisplayName(("should throw an exception if the id does not exist when deleting the entity"))
        public void confirmThrowsExceptionIfIdDoesNotExist(){

            when(planRepository.findById(89)).thenReturn(Optional.empty());

            assertThrows(PlanNoFoundException.class, () -> {
                planImplService.delete(89);
            });
        }


        @Test
        @DisplayName("verify that the plan was removed")
        public void assertInteractionWithRepositoryDelete(){

            Plan plan = new Plan("Plan familiar", new BigDecimal("600000"), PlanDuration.ANNUAL);
            when(planRepository.findById(1)).thenReturn(Optional.of(plan));

            planImplService.delete(1);

            verify(planRepository).delete(plan);
        }
    }


    @Nested()
    @DisplayName("getById")
    class getByIdTes{

        @Test
        @DisplayName("should throw an exception if the id does not exist ")
        public void assertThatLaceExceptionWhenIdIsIncorrect(){

            assertThrows(IllegalArgumentException.class, () -> {
                planImplService.getById(-1);
            });
        }


        @Test
        @DisplayName("Verify that it returns a PlanResponseDTO with the correct data")
        public void confirmThatPLanResponseDTOReturns(){

            Plan plan = new Plan("PLan compañeros de clases", new BigDecimal("4000.00"), PlanDuration.MONTLY);
            PlanResponseDTO planResponseDTO = new PlanResponseDTO(1,"PLan compañeros de clases", new BigDecimal("4000.00"), State.ACTIVE, PlanDuration.MONTLY);


            when(planRepository.findById(1)).thenReturn(Optional.of(plan));
            when(planMapper.toPlanResponseDTO(any())).thenReturn(planResponseDTO);

            PlanResponseDTO planResponse = planImplService.getById(1);

            assertThat(planResponse).isEqualTo(planResponseDTO);
        }



        @Test
        @DisplayName("Verify that it throws an exception when id does not exist")
        public void confirmThatItThrowsAnExceptionWhenIdDoesNotExist(){

            when(planRepository.findById(100))
                    .thenReturn(Optional.empty());

            assertThrows(PlanNoFoundException.class, () -> {
                planImplService.getById(100);
            });
        }
    }


    @Nested
    @DisplayName("changePlanName")
    class ChangePLanNameTest{



        @Test
        @DisplayName("Verify that an exception is thrown when the id is incorrect")
        public void assertThatExceptionIsThrownWhenTheIdIsIncorrect(){

            assertThrows(IllegalArgumentException.class, () -> {
                planImplService.getById(-1);
            });
        }





        @Test
        @DisplayName("Verify that it throws an exception when id does not exist")
        public void confirmThatItThrowsAnExceptionWhenIdDoesNotExist(){

            when(planRepository.findById(99)).thenReturn(Optional.empty());

            assertThrows(PlanNoFoundException.class, ()-> {
                planImplService.getById(99);
            });
        }


        @Test
        @DisplayName("Verify that an exception is thrown if the new name is equal to the current name")
        public void mustThrowExcewptionWhenNameIsEqualToCurrent(){

            Plan plan = new Plan("Plan de tres", new BigDecimal("80000"), PlanDuration.MONTLY);

            when(planRepository.findById(any())).thenReturn(Optional.of(plan));

            assertThrows(UnchangedValueException.class, () -> {
                planImplService.changePlanName(1, "Plan de tres");
            });
        }


        @Test
        @DisplayName("Verify that the name is changed and that the changes are saved.")
        public void mustChangeTheNameAndPersist(){

            Plan plan = new Plan("PLan de tres", new BigDecimal("80000"), PlanDuration.MONTLY);

            when(planRepository.findById(any())).thenReturn(Optional.of(plan));

            when(planRepository.save(plan)).thenReturn(plan);

            planImplService.changePlanName(1, "PLAN DE TRES");

            verify(planRepository, times(1)).save(plan);
        }
    }




    @Nested
    @DisplayName("ChangePlanPrice")
    class ChangePLanPriceTest{

        @Test
        @DisplayName("verify that the price is modified and the entity persists")
        public void assertModifyPricePersistEntity(){

            Plan plan = new Plan("PLan anual", new BigDecimal("800000"), PlanDuration.ANNUAL);


            when(planRepository.findById(any())).thenReturn(Optional.of(plan));

            when(planRepository.save(plan)).thenReturn(plan);

            planImplService.changePlanPrice(1, new BigDecimal("1000000"));


            verify(planRepository, times(1)).save(plan);
        }



        @Test
        @DisplayName("verify that it throws an exception if the new price is less than zero")
        public void shouldThrowExceptionIfPriceIsEqualToOrLesserThanZero(){

            Plan plan = new Plan("PLn de doble pelis", new BigDecimal("4000"), PlanDuration.MONTLY);

            assertThrows(IllegalArgumentException.class, ()->{
                planImplService.changePlanPrice(23, new BigDecimal("-23000"));
            });
        }


        @Test
        @DisplayName("should throw an exception if the id does not exist.")
        public void assertThatThrowsExceptionIfIdDoesNotExist(){

            assertThrows(PlanNoFoundException.class, () -> {

                planImplService.changePlanPrice(12, new BigDecimal("60000"));

            });
        }
    }


   



}