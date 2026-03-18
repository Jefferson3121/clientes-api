package com.ClientHub.api.service.impl;

import com.ClientHub.api.component.ClientMapper;
import com.ClientHub.api.domain.entity.Customer;
import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.CustomerResponseDTO;
import com.ClientHub.api.exception.ClientAlreadyExistsException;
import com.ClientHub.api.repository.CustomerRepository;
import com.ClientHub.api.repository.SubscriptionRepository;
import com.ClientHub.api.service.impl.customer.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private CustomerServiceImpl customerService;


    @Nested
    @DisplayName("registerClient")
    public class RegisterClientTest {

        @Test
        @DisplayName("Should throw ClientAlreadyExistsException when email already exists")
        public void shouldThrowExceptionWhenEmailAlreadyExists(){

            ClientRequestDTO request = new ClientRequestDTO("Juan Daniel", "emaildejuan@gmail.com", "password123");

            when(customerRepository.existsByEmail("emaildejuan@gmail.com")).thenReturn(true);

            ClientAlreadyExistsException ex = assertThrows(ClientAlreadyExistsException.class, () ->
                    customerService.registerClient(request));

            assertThat(ex.getMessage()).contains("emaildejuan@gmail.com");
        }

        @Test
        @DisplayName("Should register client successfully when email does not is register")
        public void shouldRegisterClientSuccessfully(){

            ClientRequestDTO request = new ClientRequestDTO("Juan Daniel", "emaildejuan@gmail.com", "password123");

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");

            when(customerRepository.existsByEmail("emaildejuan@gmail.com")).thenReturn(false);
            when(clientMapper.toClient(request)).thenReturn(customer);

            customerService.registerClient(request);

            verify(customerRepository, times(1)).save(customer);
        }
    }


    @Nested
    @DisplayName("activateCustomer")
    public class ActivateCustomerTest {

        @Test
        @DisplayName("Should throw IllegalArgumentException when id is negative")
        public void shouldThrowExceptionWhenIdIsNegative(){

            assertThrows(IllegalArgumentException.class, () ->
                    customerService.activateCustomer(-1));
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when customer id does not exist")
        public void shouldThrowExceptionWhenCustomerNotFound(){

            when(customerRepository.findById(99)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    customerService.activateCustomer(99));

            assertThat(ex.getMessage()).contains("99");
        }

        @Test
        @DisplayName("Should throw IllegalStateException when customer is already ACTIVE")
        public void shouldThrowExceptionWhenCustomerIsAlreadyActive(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
            customer.activate();

            when(customerRepository.findById(12)).thenReturn(Optional.of(customer));

            assertThrows(IllegalStateException.class, () ->
                    customerService.activateCustomer(12));
        }

        @Test
        @DisplayName("Should activate customer successfully when state is INACTIVE")
        public void shouldActivateCustomerSuccessfully(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");

            when(customerRepository.findById(12)).thenReturn(Optional.of(customer));

            State result = customerService.activateCustomer(12);

            assertThat(result).isEqualTo(State.ACTIVE);
        }
    }


    @Nested
    @DisplayName("deactivateCustomer")
    public class DeactivateCustomerTest {

        @Test
        @DisplayName("Should throw IllegalArgumentException when id is negative")
        public void shouldThrowExceptionWhenIdIsNegative(){

            assertThrows(IllegalArgumentException.class, () ->
                    customerService.deactivateCustomer(-1));
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when customer id does not exist")
        public void shouldThrowExceptionWhenCustomerNotFound(){

            when(customerRepository.findById(99)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    customerService.deactivateCustomer(99));

            assertThat(ex.getMessage()).contains("99");
        }

        @Test
        @DisplayName("Should throw IllegalStateException when customer is already INACTIVE")
        public void shouldThrowExceptionWhenCustomerIsAlreadyInactive(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");

            when(customerRepository.findById(12)).thenReturn(Optional.of(customer));

            assertThrows(IllegalStateException.class, () ->
                    customerService.deactivateCustomer(12));
        }

        @Test
        @DisplayName("Should throw IllegalStateException when customer has associated subscriptions")
        public void shouldThrowExceptionWhenCustomerHasSubscriptions() throws Exception {

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
            customer.activate();

            Field idField = Customer.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(customer, 5);

            when(customerRepository.findById(5)).thenReturn(Optional.of(customer));
            when(subscriptionRepository.existsByCustomerId(5)).thenReturn(true);

            assertThrows(IllegalStateException.class, () ->
                    customerService.deactivateCustomer(5));
        }

        @Test
        @DisplayName("Should deactivate customer successfully when state is ACTIVE and has no subscriptions")
        public void shouldDeactivateCustomerSuccessfully() throws Exception {

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
            customer.activate();

            Field idField = Customer.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(customer, 5);

            when(customerRepository.findById(5)).thenReturn(Optional.of(customer));
            when(subscriptionRepository.existsByCustomerId(5)).thenReturn(false);

            State result = customerService.deactivateCustomer(5);

            assertThat(result).isEqualTo(State.INACTIVE);
        }
    }


    @Nested
    @DisplayName("getByIdCustomer")
    public class GetByIdCustomerTest {

        @Test
        @DisplayName("Should throw IllegalArgumentException when id is negative")
        public void shouldThrowExceptionWhenIdIsNegative(){

            assertThrows(IllegalArgumentException.class, () ->
                    customerService.getByIdCustomer(-1));
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when customer id does not exist")
        public void shouldThrowExceptionWhenCustomerNotFound(){

            when(customerRepository.findById(99)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    customerService.getByIdCustomer(99));

            assertThat(ex.getMessage()).contains("99");
        }

        @Test
        @DisplayName("Should return CustomerResponseDTO when customer exists")
        public void shouldReturnCustomerResponseDTOWhenCustomerExists(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");

            CustomerResponseDTO responseDTO = new CustomerResponseDTO("Juan Daniel", "emaildejuan@gmail.com");

            when(customerRepository.findById(12)).thenReturn(Optional.of(customer));
            when(clientMapper.toClientResponseDTO(customer)).thenReturn(responseDTO);

            CustomerResponseDTO result = customerService.getByIdCustomer(12);

            assertThat(result).isNotNull();
            assertThat(result).isEqualTo(responseDTO);
        }
    }


    @Nested
    @DisplayName("updateCustomerName")
    public class UpdateCustomerNameTest {

        @Test
        @DisplayName("Should throw IllegalArgumentException when id is negative")
        public void shouldThrowExceptionWhenIdIsNegative(){

            ClientRequestChangeNameDTO request = new ClientRequestChangeNameDTO("Nuevo Nombre");

            assertThrows(IllegalArgumentException.class, () ->
                    customerService.updateCustomerName(-1, request));
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when customer id does not exist")
        public void shouldThrowExceptionWhenCustomerNotFound(){

            ClientRequestChangeNameDTO request = new ClientRequestChangeNameDTO("Nuevo Nombre");

            when(customerRepository.findById(99)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    customerService.updateCustomerName(99, request));

            assertThat(ex.getMessage()).contains("99");
        }

        @Test
        @DisplayName("Should update customer name successfully")
        public void shouldUpdateCustomerNameSuccessfully(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
            customer.activate();

            ClientRequestChangeNameDTO request = new ClientRequestChangeNameDTO("Nuevo Nombre");

            when(customerRepository.findById(12)).thenReturn(Optional.of(customer));

            customerService.updateCustomerName(12, request);

            assertThat(customer.getName()).isEqualTo("Nuevo Nombre");
        }
    }


    @Nested
    @DisplayName("updateCostumerEmail")
    public class UpdateCustomerEmailTest {

        @Test
        @DisplayName("Should throw IllegalArgumentException when id is negative")
        public void shouldThrowExceptionWhenIdIsNegative(){

            ClientRequestChangeEmailDTO request = new ClientRequestChangeEmailDTO("nuevoemail@gmail.com");

            assertThrows(IllegalArgumentException.class, () ->
                    customerService.updateCostumerEmail(-1, request));
        }

        @Test
        @DisplayName("Should throw EntityNotFoundException when customer id does not exist")
        public void shouldThrowExceptionWhenCustomerNotFound(){

            ClientRequestChangeEmailDTO request = new ClientRequestChangeEmailDTO("nuevoemail@gmail.com");

            when(customerRepository.findById(99)).thenReturn(Optional.empty());

            EntityNotFoundException ex = assertThrows(EntityNotFoundException.class, () ->
                    customerService.updateCostumerEmail(99, request));

            assertThat(ex.getMessage()).contains("99");
        }

        @Test
        @DisplayName("Should throw IllegalArgumentException when new email is the same as current email")
        public void shouldThrowExceptionWhenEmailIsTheSame(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
            customer.activate();

            ClientRequestChangeEmailDTO request = new ClientRequestChangeEmailDTO("emaildejuan@gmail.com");

            when(customerRepository.findById(12)).thenReturn(Optional.of(customer));

            assertThrows(IllegalArgumentException.class, () ->
                    customerService.updateCostumerEmail(12, request));
        }

        @Test
        @DisplayName("Should update customer email successfully")
        public void shouldUpdateCustomerEmailSuccessfully(){

            Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
            customer.activate();

            ClientRequestChangeEmailDTO request = new ClientRequestChangeEmailDTO("nuevoemail@gmail.com");

            when(customerRepository.findById(12)).thenReturn(Optional.of(customer));

            customerService.updateCostumerEmail(12, request);

            assertThat(customer.getEmail()).isEqualTo("nuevoemail@gmail.com");
        }
    }
}