package com.ClientHub.api.service.impl;

import com.ClientHub.api.component.PayMapper;
import com.ClientHub.api.domain.entity.Customer;
import com.ClientHub.api.domain.entity.Pay;
import com.ClientHub.api.domain.entity.Plan;
import com.ClientHub.api.domain.entity.Subscription;
import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.dto.request.CreatePayRequestDTO;
import com.ClientHub.api.dto.response.PayResponseDTO;
import com.ClientHub.api.repository.PayRepository;
import com.ClientHub.api.repository.SubscriptionRepository;
import com.ClientHub.api.service.impl.pay.PayServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PayServiceImplTest {

    @Mock
    private PayRepository payRepository;

    @Mock
    private SubscriptionRepository susbcriptionRepsitory;

    @Mock
    private PayMapper payMapper;

    @InjectMocks
    private PayServiceImpl payService;


    @Nested
    @DisplayName("createPay")
    public class CreatePayTest {

        @Test
        @DisplayName("Should throw EntityNotFoundException when subscription id does not exist")
        public void shouldThrowExceptionWhenSubscriptionNotFound(){

            CreatePayRequestDTO request = new CreatePayRequestDTO(99, new BigDecimal("50000"));

            when(susbcriptionRepsitory.findById(99)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    payService.createPay(request));

            assertThat(ex.getMessage()).contains("Entidad no encontrada");
        }

        @Test
        @DisplayName("Should create pay and return its DTO successfully")
        public void shouldCreatePayAndReturnDTO(){

            Customer customer = new Customer("Gissel", "emaildegissel@gmail.com");
            Plan plan = new Plan("Plan estudiantil", new BigDecimal("20000"), PlanDuration.MONTLY);
            Subscription subscription = new Subscription(plan, customer);

            CreatePayRequestDTO request = new CreatePayRequestDTO(12, new BigDecimal("50000"));

            Pay pay = Pay.create(subscription, new BigDecimal("50000"));

            PayResponseDTO payResponseDTO = new PayResponseDTO(1, 12, new BigDecimal("50000"), LocalDate.now());

            when(susbcriptionRepsitory.findById(12)).thenReturn(Optional.of(subscription));
            when(payRepository.save(any(Pay.class))).thenReturn(pay);
            when(payMapper.toPayResponseDTO(pay)).thenReturn(payResponseDTO);

            PayResponseDTO result = payService.createPay(request);

            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(payResponseDTO);
        }
    }


    @Nested
    @DisplayName("getPay")
    public class GetPayTest {


        @Test
        @DisplayName("Should throw IllegalArgumentException when id is zero or less")
        public void shouldThrowExceptionWhenIdIsZeroOrLess(){

            assertThrows(IllegalArgumentException.class, () ->
                    payService.getPay(0));
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when pay id does not exist")
        public void shouldThrowExceptionWhenPayNotFound(){

            when(payRepository.findById(99)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    payService.getPay(99));

            assertThat(ex.getMessage()).contains("99");
        }

        @Test
        @DisplayName("Should return PayResponseDTO when pay exists")
        public void shouldReturnPayResponseDTOWhenPayExists(){

            Customer customer = new Customer("Felipe Montañes", "emaildefelipe@gmail.com");
            Plan plan = new Plan("Plan fin de año", new BigDecimal("78000"), PlanDuration.MONTLY);
            Subscription subscription = new Subscription(plan, customer);

            Pay pay = Pay.create(subscription, new BigDecimal("78000"));

            PayResponseDTO payResponseDTOExpected = new PayResponseDTO(1, 12, new BigDecimal("78000"), LocalDate.now());

            when(payRepository.findById(12)).thenReturn(Optional.of(pay));
            when(payMapper.toPayResponseDTO(pay)).thenReturn(payResponseDTOExpected);

            PayResponseDTO result = payService.getPay(12);

            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(payResponseDTOExpected);
        }
    }
}
