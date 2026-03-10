package com.ClientHub.api.domain;


import com.ClientHub.api.domain.enums.State;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;


@Getter
@Entity
@Table(name = "client") // como cambiar el nombre en la bd
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private State state;

    protected Customer() {
    }

    public Customer(String name, String email) {

        validateString(name);
        validateString(email);

        this.name = name;
        this.email = email;
        this.state = State.INACTIVE;
    }


    public void updateName(String name) {

        validateString(name);
        ensureIsInactive();

        if (this.name.equals(name)) {
            throw new IllegalArgumentException("No puede ingresar el nombre actual del usuario");
        }

        this.name = name;
    }


    public void updateEmail(String newEmail){

        validateString(newEmail);
        ensureIsInactive();

        if (this.email.equals(newEmail)){
            throw new IllegalArgumentException("Nuevo email no puede ser igual a email actual");
        }

        this.email = newEmail;
    }

    public void activate(){
        this.state = State.ACTIVE;
    }

    public void deactivate(){
        this.state = State.INACTIVE;
    }


    private void ensureIsInactive(){

        if (state == State.INACTIVE){
            throw new IllegalStateException("Estado de la entidad invalido para esta operacion");
        }
    }

    private void validateString(String string){
        if (string == null || string.isBlank()){
            throw new IllegalArgumentException("Valor invalido");
        }
    }


    @Override
    public boolean equals(Object obj){
        if (obj == this) return true;

        if (obj == null || obj.getClass() != this.getClass()) return false;

        Customer customer = (Customer) obj;

        return Objects.equals(this.id, customer.id);
    }

    @Override
    public int hashCode(){
        return Objects.hashCode(id);
    }
}
