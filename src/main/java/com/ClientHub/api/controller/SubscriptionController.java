package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.SubscriptionRequestDTO;
import com.ClientHub.api.dto.response.SubscriptionResponseDTO;
import com.ClientHub.api.service.contrat.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;



@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
@Tag(name = "Subscription", description = "Define una susbcripcion de un cliente a un plan comercial")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;


    @Operation(
            summary = "Crar una subscripcion",
            description = "Crea una nueva susbcripcion de un cliente en especifico a un plan comercial en especificio",

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos requeridos para la creacion de una subscripcion",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = SubscriptionRequestDTO.class)
                    )
            ),

            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Subscripcion creada correctamente asociada a un cliente y una plan comercial"
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos imcompletos o imcompletos"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe ningun plan o/y cliente asociado a el identificador unico porporcionado"
                    )

                    //Agregar responseCode para 409 cuando el cliente ya existe
            }
    )
    @PostMapping
    public ResponseEntity<SubscriptionResponseDTO> createSubscription(@Valid @RequestBody SubscriptionRequestDTO subscriptionRequestDTO) {
        SubscriptionResponseDTO subscriptionResponse = subscriptionService.createSubscription(subscriptionRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionResponse);
    }


    @Operation(
            summary = "Eliminar susbcripcion",
            description = "Eliminar completamente del sistema una subscripcion, esta accion solo se ejecutara si la susbcripcion no esta en estado CANCELLED",


            responses = {

                    @ApiResponse(
                            responseCode = "204",
                            description = "Subscripcion eliminada correctamente"
                    ),


                    @ApiResponse(
                            responseCode = "400",
                            description = "Identificador unico incorrecto"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe ninguna suscripcion asociada a el identificador unico proporcionado"
                    ),

                    @ApiResponse(
                            responseCode = "409",
                            description = "No se puede eliminar una susbcripcion cullo estado no sea CANCELLED"
                    )
            }
    )

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Integer id) {

        subscriptionService.deleteSubscription(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(
            summary = "Activar una subscripcion",
            description = "Activa una subscripcion de un cliente",


            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Subscripcion activada correctamente"
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Identificador unico incorrecto o faltante"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontro ninguna subscripcion asociada a el identificador unico porporcionado"
                    ),

                    @ApiResponse(
                            responseCode = "409",
                            description = "La subscripcion ya esta activada, no se puede activar una subscripcion que ya esta activa"
                    )
            }
    )




    @PatchMapping("/{id}/activate")
    public ResponseEntity<Void> activateSubscription(@PathVariable Integer id) {

        subscriptionService.activateSubscription(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(
            summary = "Cancelar subscripcion",
            description = "Cancelar comercialment la subscripcion de un cliente",


            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Subscripcion cancelada correctamente"
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Identificador unico proporcionado incorrecto"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontro ninguna subscripcion asociada a el identificador unico proporcionado"
                    ),

                    @ApiResponse(
                            responseCode = "409",
                            description = "La subscripcion ya esta cancela"
                    )
            }
    )



    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelSubscription(@PathVariable Integer id) {

        subscriptionService.cancelSubscription(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }



    @Operation(
            summary = "Renovar subscripcion",
            description = "Renueva una subcripcion comercial de un cliente",


            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "Subscripcion comercial de un cliente renovada exitosamente"
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Identificador unico de la subscripcion incorrecto"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "No se encontro ningunna subscripcion asociada a el identificador unico proporcionado"
                    ),

                    @ApiResponse(
                            responseCode = "409",
                            description = "La subscripcion ya esta activa"
                    )


            }
    )
    @PatchMapping("/{id}/renew")
    public ResponseEntity<Void> renewSubscription(@PathVariable Integer id) {


        subscriptionService.renewSubscription(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
