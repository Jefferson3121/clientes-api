package com.ClientHub.api.domain;


import com.ClientHub.api.domain.enums.State;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NonNull;


@Getter
@Entity
@Table(name = "client")
public class Client {

   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   @Column(nullable = false) @NonNull
    private String name;

   @Column(unique = true, nullable = false) @Email @NonNull
    private String email;

    @Enumerated(EnumType.STRING)
    private State state;

    protected Client(){
        this.state = State.INACTIVE;
    }

    public Client(@NonNull String name,@NonNull String email){
        this();

        validateString(name);
        validateString(email);

        this.name = name;
        this.email = email;
    }


    public void updateName(String name){

        validateString(name);
        validateState();

        if (this.name.equals(name)){
            throw new IllegalArgumentException("No puede ingresar el nombre actual del usuario");
        }

        this.name = name;
    }


    public void updateEmail(String newEmail){

        validateString(newEmail);
        validateState();

        if (this.email.equals(newEmail)){
            throw new IllegalArgumentException("Nuevo email no puede ser igual a email actual");
        }

        this.email = newEmail;
    }


    public  void updateState(){
        if (this.state == State.INACTIVE){
            this.state = State.ACTIVE;
        }else {
            this.state = State.INACTIVE;
        }
    }


    private void validateState(){

        if (state != State.INACTIVE){
            throw new IllegalStateException("Estado de la entidad invalido para esta operacion");
        }
    }

    private void validateString(String string){
        if (string == null || string.isBlank()){
            throw new IllegalArgumentException("Valor invalido");
        }
    }
}
