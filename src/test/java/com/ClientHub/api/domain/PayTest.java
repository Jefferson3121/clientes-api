package com.ClientHub.api.domain;


import com.ClientHub.api.domain.entity.Customer;
import com.ClientHub.api.domain.entity.Pay;
import com.ClientHub.api.domain.entity.Plan;
import com.ClientHub.api.domain.entity.Subscription;
import com.ClientHub.api.domain.enums.PlanDuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PayTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when subscription is null")
    public void shouldThrowExceptionWhenSubscriptionIsNull(){

        assertThrows(IllegalArgumentException.class, () ->
                Pay.create(null, new BigDecimal("50000")));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when valuePay is null")
    public void shouldThrowExceptionWhenValuePayIsNull(){

        Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
        Plan plan = new Plan("Plan familiar", new BigDecimal("70000"), PlanDuration.MONTLY);
        Subscription subscription = new Subscription(plan, customer);

        assertThrows(IllegalArgumentException.class, () ->
                Pay.create(subscription, null));
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when valuePay is zero or less")
    public void shouldThrowExceptionWhenValuePayIsZeroOrLess(){

        Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
        Plan plan = new Plan("Plan familiar", new BigDecimal("70000"), PlanDuration.MONTLY);
        Subscription subscription = new Subscription(plan, customer);

        assertThrows(IllegalArgumentException.class, () ->
                Pay.create(subscription, BigDecimal.ZERO));
    }

    @Test
    @DisplayName("Should create Pay successfully with valid data")
    public void shouldCreatePaySuccessfullyWithValidData(){

        Customer customer = new Customer("Juan Daniel", "emaildejuan@gmail.com");
        Plan plan = new Plan("Plan familiar", new BigDecimal("70000"), PlanDuration.MONTLY);
        Subscription subscription = new Subscription(plan, customer);

        Pay pay = Pay.create(subscription, new BigDecimal("50000"));

        assertThat(pay).isNotNull();
        assertThat(pay.getSubscription()).isEqualTo(subscription);
        assertThat(pay.getValuePay()).isEqualTo(new BigDecimal("50000"));
        assertThat(pay.getDatePay()).isEqualTo(LocalDate.now());
    }
}