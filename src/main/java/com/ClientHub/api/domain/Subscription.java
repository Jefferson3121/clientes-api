package com.ClientHub.api.domain;

import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.StateSubscription;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Entity
@Getter
@Table(name = "subscription")
public class Subscription {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    private Client client;

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

    public Subscription(Plan plan, Client client){
        this();

        if (plan == null || client == null) throw new NullPointerException("client o plan son null");

        this.plan = plan;
        this.client = client;
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

    public boolean isCancelled(){
        return this.state == StateSubscription.EXPIRED;
    }

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

        if (this.plan.getPlanDuration() == PlanDuration.MONTLY){
            this.dateEnd = dateStar.plusMonths(1);
        } else if (this.plan.getPlanDuration() == PlanDuration.ANNUAL) {
            this.dateEnd = this.dateStar.plusYears(1);
        }
    }












}
