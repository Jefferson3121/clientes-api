package com.ClientHub.api.domain.entity;

import com.ClientHub.api.exception.UnchangedValueException;
import com.ClientHub.api.domain.enums.PlanDuration;
import com.ClientHub.api.domain.enums.State;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;


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
    @Column(name = "plan_duration")
    private PlanDuration duration;

    protected Plan(){
    }


    public Plan(String name, BigDecimal price, PlanDuration duration){

        this();

        validateName(name);
        validatePrice(price);

        this.name = name;
        this.price = price;
        this.duration = duration;
        this.state = State.INACTIVE;
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



    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;

        if (obj == null || obj.getClass() != this.getClass()) return false;

        Plan plan = (Plan) obj;

        return Objects.equals(this.id, ((Plan) obj).id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }


}
