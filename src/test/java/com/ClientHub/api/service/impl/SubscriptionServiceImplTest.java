package com.ClientHub.api.service.impl.subscription;


import com.ClientHub.api.component.SubscriptionMapper;
import com.ClientHub.api.domain.entity.Customer;
import com.ClientHub.api.domain.entity.Plan;
import com.ClientHub.api.domain.entity.Subscription;
import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.domain.enums.StateSubscription;
import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import com.ClientHub.api.repository.CustomerRepository;
import com.ClientHub.api.repository.PlanRepository;
import com.ClientHub.api.repository.SubscriptionRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceImplTest {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PlanRepository planRepository;

    @Mock
    private SubscriptionMapper subscriptionMapper;


    @InjectMocks
    private SubscriptionServiceImpl subscriptionService;


    @Nested
    @DisplayName("createSubscription")
    public class CreateSubscriptionTest{


        @Test
        @DisplayName("should throw EntityNotFoundException when customer id does not exist")
        public void shouldThrowExceptionWhenCustomerNotFound(){

            SubscriptionRequestDTO  request = new SubscriptionRequestDTO(99, 23);

            when(customerRepository.findById(99)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    subscriptionService.createSubscription(request));

            assertThat(ex.getMessage()).contains("99");
        }



        @Test
        @DisplayName("should throw an exception if there is no PLAN associated with the provided ID")
        public void shouldThrowExceptionWhenPlanNotFound(){

            SubscriptionRequestDTO request = new SubscriptionRequestDTO(23, 78);

            Customer mockCustomer = new Customer("Juan Fernado", "juanfernado@gmail.com");

            when(customerRepository.findById(23)).thenReturn(Optional.of(mockCustomer));

            when(planRepository.findById(78)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    subscriptionService.createSubscription(request));

            assertThat(ex.getMessage()).contains("78");
        }


        @Test
        @DisplayName("Crear una nuevo obketo subscripcion y retornar su dto")
        public void deberiaCrearUnaNUevaSubscriptcionRetornarSuDto(){

            SubscriptionRequestDTO request = new SubscriptionRequestDTO(12, 45);

            Customer customerMock = new Customer("Julieta Vanegas", "julietavanegas89@gmail.com");

            Plan planMock = new Plan("Plan familiar", new BigDecimal("70000"), PlanDuration.MONTLY);

            Subscription subscriptionExpected = new Subscription(planMock, customerMock);

            SubscriptionResponseDTO subscriptionResponseDTOExpected = new SubscriptionResponseDTO(1, 12,45,LocalDate.now() ,LocalDate.now().plusMonths(1), StateSubscription.ACTIVE);




            when(customerRepository.findById(12)).thenReturn(Optional.of(customerMock));

            when(planRepository.findById(45)).thenReturn(Optional.of(planMock));

            when(subscriptionRepository.save(any(Subscription.class))).thenReturn(subscriptionExpected);

            when(subscriptionMapper.toSubscriptionResponseDTO(subscriptionExpected)).thenReturn(subscriptionResponseDTOExpected);



            SubscriptionResponseDTO result = subscriptionService.createSubscription(request);

            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(subscriptionResponseDTOExpected);
        }




    }


    @Nested
    @DisplayName("delteSeubscription")
    public class DeleteSubscriptionTest{

        @Test
        @DisplayName("Deberia lanzar EntityNotFoundException cuando no existe una subscripcion con id porporcionado")
        public void deberiaLanzarExcepcionSiSubscriptionNoExiste(){

            when(subscriptionRepository.findById(86)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    subscriptionService.deleteSubscription(86));

            assertThat(ex.getMessage()).contains(String.format("Entidad con id: %d no existe", 86));

        }


        @Test
        @DisplayName("Deberia lanzar excepcion cuando el estado de la subscripcion es ACTIVE")
        public void deberiaLanzarExcepcionCuandoSubscripcionEstaActiva(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
            Plan plan = new Plan("Plan 2 x 1", new BigDecimal("29000"), PlanDuration.MONTLY);

            Subscription subscription = new Subscription(plan, customer);

            when(subscriptionRepository.findById(13)).thenReturn(Optional.of(subscription));

            IllegalStateException ex = assertThrows(IllegalStateException.class, () ->
                    subscriptionService.deleteSubscription(13));

            assertThat(ex.getMessage()).contains("No puede eliminar una subscripcion que este en estado ACTIVE");

        }



        //En este metodo tengo la duda de que se hace, ya que la entidad subscription se crea con estado Inactive pero no recibe el valor por el contructor sino que es por defecto, tampoco tiene set porque la entidad se supone es DDD y el metodo de test ovbiamente falla porque no puede borrar una subscripcion en esrado ACTIVE
        @Test
        @DisplayName("Debria eliminar una subscripcion del sistema")
        public void deberiaEliminarUnaSubscrpcion(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
            Plan plan = new Plan("Plan 3 x 2", new BigDecimal("46000"), PlanDuration.MONTLY);

            Subscription subscription = new Subscription(plan, customer);

            when(subscriptionRepository.findById(45)).thenReturn(Optional.of(subscription));

            verify(subscriptionRepository, times(1)).delete(subscription);

        }
    }



    @Nested
    @DisplayName("ActivateSubscription")
    public class ActivateSubscriptionTest{


        @Test
        @DisplayName("should throw EntityNotFoundException when subscription id does not exist")
        public void shouldThrowEntityNotFoundExceptionWhenSubscriptionDoesNotExist(){

            when(subscriptionRepository.findById(987)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    subscriptionService.deleteSubscription(987));

            assertThat(ex.getMessage()).contains(String.format("Entidad con id: %d no existe", 987));
        }





        @Test
        @DisplayName("should throw IllegalStateException when subscription state is not EXPIRED")
        public void shouldThrowExceptionWhenSubscriptionStateIsNotExpired(){

            Customer customer = new Customer("Iban Ramirez", "emaildeivan@gmail.com");
            Plan plan = new Plan("Plan Familiar 2 x 1", new BigDecimal("80000"), PlanDuration.MONTLY);

            Subscription subscription = new Subscription(plan, customer);

            when(subscriptionRepository.findById(13)).thenReturn(Optional.of(subscription));



            assertThrows(IllegalStateException.class, () ->
                    subscriptionService.activateSubscription(13));

        }





        @Test
        @DisplayName("should activate subscription successfully when current state is EXPIRED")
        public void shouldActivateSubscriptionWhenStateIsExpired(){

            Customer customer = new Customer("Gissel", "emaildegissel@gmail.com");
            Plan plan = new Plan("Plan estudiantil", new BigDecimal("20000"), PlanDuration.MONTLY);

            Subscription subscription = new Subscription(plan, customer);
            subscription.expire();

            when(subscriptionRepository.findById(12)).thenReturn(Optional.of(subscription));

            subscriptionService.activateSubscription(12);

            assertThat(subscription.getState()).isEqualTo(StateSubscription.ACTIVE);
        }
    }



    @Nested
    @DisplayName("cancelSubscription")
    public class cancelSubscriptionTest{

        @Test
        @DisplayName("Debe lanzar excepcion si el id de la subscripcion no existe")
        public void deberiaLanarExcepcionSiSubscripcionIdNoExiste(){

            when(subscriptionRepository.findById(94)).thenReturn(Optional.empty());

           assertThrows(EntityNotFoundException.class, () ->
                   subscriptionService.cancelSubscription(94));


        }



        @Test
        @DisplayName("El nuevo estado de subscripcion debe ser CANCELLED")
        public void confirmarNuevoEstadoDeSubscripcionEsCancelled(){

            Plan plan = new Plan("Plan fin de año", new BigDecimal("78000"), PlanDuration.MONTLY);

            Customer customer = new Customer("Felipe Montañes", "emaildefelipe@gmail.com");

            Subscription subscription = new Subscription(plan, customer);


            when(subscriptionRepository.findById(54)).thenReturn(Optional.of(subscription));

            subscriptionService.cancelSubscription(54);

            assertThat(subscription.getState()).isEqualTo(StateSubscription.CANCELLED);
        }
    }







    @Nested
    @DisplayName("renewSubscription")
    public class RenewSubscriptionTest{

        @Test
        @DisplayName("Must throw an exception if the subscription ID does not exist")
        public void shouldThrowExceptionIfSubscriptionIdDoesNotExist(){

            when(subscriptionRepository.findById(94)).thenReturn(Optional.empty());

            assertThrows(EntityNotFoundException.class, () ->
                    subscriptionService.renewSubscription(94));


            verify(subscriptionRepository, times(1)).findById(94);
        }



        @Test
        @DisplayName("Should throw IllegalStateException if subscription is CANCELLED")
        public void shouldThrowIllegalStateExceptionIfSubscriptionIsCancelled(){

            Plan plan = new Plan("Plan fin de año", new BigDecimal("78000"), PlanDuration.MONTLY);

            Customer customer = new Customer("Felipe Montañes", "emaildefelipe@gmail.com");

            Subscription subscription = new Subscription(plan, customer);
            subscription.cancel();

            when(subscriptionRepository.findById(54)).thenReturn(Optional.of(subscription));

            assertThrows(IllegalStateException.class, () ->
                    subscriptionService.renewSubscription(54));
        }



        @Test
        @DisplayName("The new subscription status must be ACTIVE")
        public void shouldConfirmNewSubscriptionStateIsActive(){

            Plan plan = new Plan("Plan fin de año", new BigDecimal("78000"), PlanDuration.MONTLY);

            Customer customer = new Customer("Felipe Montañes", "emaildefelipe@gmail.com");

            Subscription subscription = new Subscription(plan, customer);

            when(subscriptionRepository.findById(54)).thenReturn(Optional.of(subscription));

            subscriptionService.renewSubscription(54);

            assertThat(subscription.getState()).isEqualTo(StateSubscription.ACTIVE);
        }
    }

}