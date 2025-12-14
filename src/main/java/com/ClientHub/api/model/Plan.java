package com.ClientHub.api.model;

import com.ClientHub.api.exception.UnchangedValueException;
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
    private StatePlan state;

    protected Plan(){};


    public Plan(String name, BigDecimal price){

        validateName(name);
        validatePrice(price);

        this.name = name;
        this.price = price;
        this.state = StatePlan.INACTIVE;
    }

    public void modifyName(String newName){

        validateName(name);

        this.name = name;
    }


    public void modifyPrice(BigDecimal price){

        validatePrice(price);

        this.price = price;

    }

    public void modifyState(){

        if (this.state == StatePlan.INACTIVE){
            this.state = StatePlan.ACTIVE;
        }else  {
            this.state =  StatePlan.INACTIVE;
        }

    }



    private void validateName(String name){

        if (name == null || name.isBlank() || name.isEmpty()){
            throw new IllegalArgumentException("Name invalido");
        }

        if (name == this.name){
            throw new UnchangedValueException("Name ingresado es igual a name actual");
        }
    }

    private void validatePrice(BigDecimal price){
        if (price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price inavalido");
        }
    }


}
