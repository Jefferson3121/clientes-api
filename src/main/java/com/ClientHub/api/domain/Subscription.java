package com.ClientHub.api.domain;

import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.StateSubscription;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Getter
@Table(name = "subscription")
public class Subscription {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "client_id")
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "plan_id")
    private Plan plan;

    @Column(name = "date_star", nullable = false)
    private LocalDate dateStar;

    @Column(name = "date_end", nullable = false)
    private LocalDate dateEnd;

    @Enumerated(EnumType.STRING)
    private StateSubscription state;


    protected Subscription(){
        this.state = StateSubscription.ACTIVE;
    }

    public Subscription(Plan plan, Customer customer){
        this();

        if (plan == null || customer == null) throw new NullPointerException("customer o plan son null");

        this.plan = plan;
        this.customer = customer;
    }



    public void isTrueToCancel(){
        if (isCancelled()){
            throw new IllegalStateException(String.format("La suscripcion esta canecelada"));
        }
    }

    public boolean isActive(){
        return this.state == StateSubscription.ACTIVE;
    }

    public boolean isExpired(){
        return this.state == StateSubscription.EXPIRED;
    }

    public boolean isCancelled(){return this.state == StateSubscription.CANCELLED;}

    public void activate(){
        if (isActive()){
            throw new IllegalStateException("La suscripcion ya esta activa");
        }

        this.state = StateSubscription.ACTIVE;
    }


    public void renew(){

        if (isCancelled()){
            throw new IllegalStateException("Erro: No puede reactivar/renovar una suscripcion cancelada");
        }

        this.state = StateSubscription.ACTIVE;
    }

    public void cancel(){
        this.state = StateSubscription.CANCELLED;
    }



    @PrePersist
    public void dateStar(){
        this.dateStar = LocalDate.now();
        calculateEndDate();
    }


    public void calculateEndDate(){

        if (plan.getDuration() == null) {
            throw new IllegalStateException("Duración de plan  no inicializados");
        }


        if (this.plan.getDuration() == PlanDuration.MONTLY){
            this.dateEnd = dateStar.plusMonths(1);
        } else if (this.plan.getDuration() == PlanDuration.ANNUAL) {
            this.dateEnd = this.dateStar.plusYears(1);
        }
    }




    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;

        if (obj == null || obj.getClass() != this.getClass()) return false;

        Subscription subscription = (Subscription) obj;

        return Objects.equals(this.id, subscription.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }











}
