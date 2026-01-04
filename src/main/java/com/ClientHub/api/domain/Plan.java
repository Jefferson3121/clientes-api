package com.ClientHub.api.domain;

import com.ClientHub.api.exception.UnchangedValueException;
import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.State;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;



@Getter
@Entity
public class Plan {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(value = EnumType.STRING)
    private PlanDuration planDuration;

    protected Plan(){
        this.state = State.INACTIVE;
    }


    public Plan(String name, BigDecimal price, PlanDuration planDuration){

        this();

        validateName(name);
        validatePrice(price);

        this.name = name;
        this.price = price;
        this.planDuration = planDuration;
    }

    public void modifyName(String newName){

        validateName(newName);

        this.name = newName;
    }


    public void modifyPrice(BigDecimal price){

        validatePrice(price);

        this.price = price;

    }

    public void modifyState(){

        if (this.state == State.INACTIVE){
            this.state = State.ACTIVE;
        }else  {
            this.state =  State.INACTIVE;
        }

    }



    private void validateName(String name){

        if (name == null || name.isBlank() || name.isEmpty()){
            throw new IllegalArgumentException("Name invalido");
        }

        if (name.equals(this.name)){
            throw new UnchangedValueException("Name ingresado es igual a name actual");
        }
    }

    private void validatePrice(BigDecimal price){
        if (price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price inavalido");
        }
    }


}
