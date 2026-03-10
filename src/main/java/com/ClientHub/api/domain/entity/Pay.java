package com.ClientHub.api.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Pay {

    @Id   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "suscription_id")
    private Subscription subscription;

    @Column(name = "value_pay", nullable = false)
    private BigDecimal valuePay;

    @Column(name = "date_day", nullable = false)
    private LocalDate datePay;


    public static Pay create(Subscription subscription, BigDecimal valuePay){
        Objects.requireNonNull(subscription, "La Susccripcion no puede ser null");
        Objects.requireNonNull(valuePay, "EL pavlor del pago no pude ser null");

        if (valuePay.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("El valor del pago debe ser mayor a cero");

        return new Pay(subscription, valuePay, LocalDate.now());
    }


    public Pay(Subscription subscription, BigDecimal valuePay, LocalDate datePay){
        this.subscription = subscription;
        this.valuePay = valuePay;
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;

        if (obj == null || obj.getClass() != this.getClass()) return false;

        Pay pay = (Pay) obj;

        return Objects.equals(this.id, pay.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }
}
