package com.ClientHub.api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Entity
@Table(name = "cliente")
public class Client {

   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   @Column(nullable = false) @NonNull
    private String name;
   @Column(unique = true, nullable = false) @Email @NonNull
    private String email;

    private StateClient state;
    protected Client(){}

    public Client(String name, String email){
        this.name = name;
        this.email = email;
        this.state = StateClient.INACTIVE;
    }


    public void updateName(@NonNull String name){
        if (this.name.equals(name)){
            throw new IllegalArgumentException("No puede ingresar el nombre actual del usuario");
        }

        this.name = name;
    }


    public void updateEmail(@NonNull String newEmail){

        if (this.email.equals(newEmail)){
            throw new IllegalArgumentException("Nuevo email no puede ser igual a email actual");
        }

        this.email = newEmail;


    }


    public  void updateState(){
        if (this.state == StateClient.INACTIVE){
            this.state = StateClient.ACTIVE;
        }else {
            this.state = StateClient.INACTIVE;
        }
    }







}
