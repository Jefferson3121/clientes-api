package com.ClientHub.api.controller;

import com.ClientHub.api.domain.enums.State;
import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.CustomerResponseDTO;
import com.ClientHub.api.dto.response.ResponseError;
import com.ClientHub.api.service.contrat.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/client")
@Tag(name = "Client", description = "Operaciones crud relacionadas con Client")
public class ClientController {

    private final CustomerService customerService;


    @Operation(
            summary = "Registrar un nuevo cliente",
            description = "Regitrar un nuevo cliente al sistema verificando que no exista otro",

            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto que contiene la informacion necesaria para crear un cliente",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClientRequestDTO.class)
                    )
            ),

            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Cliente creado correctamente",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Cliente con el email proporcionado ya existe",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = ResponseError.class),
                                    examples = @ExampleObject(
                                            """
                                            {
                                              "status": 409,
                                              "message": "Cliente con el email emaildecliente@gmail.com ya existe",
                                              "timestamp": "2026-02-15T11:41:39.9371288"
                                            }
                                            """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Peticion incorrecta"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )


    @PostMapping
    public ResponseEntity<Void> registerClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {

        customerService.registerClient(clientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }







    @Operation(
            summary = "Obtener un cliente",
            description = "Obtener un cliente por el identificador unico",

            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cuerpo de Client con sus datos en formato Json"
                    ),
                    @ApiResponse(responseCode = "404",
                            description = "Cliente no encontrado"
                    ),
                    @ApiResponse(responseCode = "400",
                            description = "Id incorrecto"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )

            }
    )




    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getByIdClient(@PathVariable int id) {
        CustomerResponseDTO responseDTO = customerService.getByIdCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }





    @Operation(
            summary = "Actualizar nombre",
            description = "Actualizar el nombre de un cliente",


            requestBody =  @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevo nombre del cliente",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClientRequestChangeNameDTO.class)
                    )
            ),


            responses = {

                    @ApiResponse(
                            responseCode = "200",
                            description = "Nombre actualizado correctamente"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Id invalido o datos del request icorrectoe(validacion fallida) o el nuevo nombre es igual a el nombre actual de cliente"
                    ),      // quitar ultimo (o) cuando se cambien la excepcion que se lanza al recibir el mismo nombre al actual
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado con el id proporcionado"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
                    // @ApiResponse(responseCode = "500", description = "No es posible modificar este cliente si esta inactivo"), --> Se debe mejrar dejandolo no tan generico por eso lo comento, tambien se debe mejorar la excepcion que auise lanza (IlegalStateException
            }
    )
    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> updateClientName(@PathVariable int id, @Valid @RequestBody ClientRequestChangeNameDTO clientRequestChangeNameDTO) {
        customerService.updateCustomerName(id, clientRequestChangeNameDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }







    @Operation(
            summary = "Actualizar email",
            description = "Actualizar el email de un cliente",


            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Nuevo email del cliente",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ClientRequestChangeEmailDTO.class)
                    )
            ),



            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Email atualizado correctamente"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "El nuevo email es igual a el email actual por ende no se puede actualizar o formato de email invalido"
                    ),//la primera respuesta no deberia estar aqui, mejorar eso
                    // @ApiResponse( responseCode = "409", description = "EL email ya esta usado por otro usuario") agregar mas adelante, valida que no se este permitiendo duplicados de email
                    @ApiResponse(responseCode = "404",
                            description = "Cliente no encontrado con el id proporcionado"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )

    @PatchMapping("/{id}/email")
    public ResponseEntity<Void> updateClientEmail(

            @PathVariable int id,
            @Valid @RequestBody ClientRequestChangeEmailDTO changeEmailDTO) {

        customerService.updateCostumerEmail(id, changeEmailDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }




    @Operation(
            summary = "Activar cliente",
            description = "Activa un cliente previamente inactivo mediante su identifiador unico",

            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente activado correctamente",
                            content = @Content(
                                    schema = @Schema(type = "string", example = "ACTIVE")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado con el id proporcionado"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "El cliente ya se encuentra activo "
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )

    @PatchMapping("/{id}/activate")
    public ResponseEntity<State> activateCustomer(@PathVariable int id){

        State stateResponse = customerService.activateCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).body(stateResponse);
    }






    @Operation(
            summary = "Desactivar cliente",
            description = "Desactiva un cliente activo mediante su identificador unico",

            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Cliente desactivado correctamente",
                            content = @Content(
                                    schema = @Schema(type = "string", example = "INACTIVE")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Cliente no encontrado con el id proporcionado"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "El cliente ya se encuentra inactivo"
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor"
                    )
            }
    )


    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<State> deactivateCustomer(@PathVariable int id){

        State stateResponse = customerService.deactivateCustomer(id);

        return ResponseEntity.status(HttpStatus.OK).body(stateResponse);
    }


}
