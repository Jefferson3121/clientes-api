package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.dto.response.ResponseError;
import com.ClientHub.api.service.contrat.PlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/plan")
@Tag(name = "Plan", description = "Operaciones CRUD relacinadas con la entidad Plan")
public class PlanController {

    private final PlanService planService;


    @Operation(
            summary = "Crear un nuevo plan",
            description = "Agrega y crea un nuevo plan al sistema con los datos que lo representan",


            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos requeridos para la creacion de un PLan",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = PLanRequestDTO.class)
                    )
            ),

            responses = {@ApiResponse(
                    responseCode = "201",
                    description = "Plan creado con exito mas informacion del plan creado"
            ),
                    //Falta agregar respuesta de cuadno el plan y existe, esperar a que se haga la excepcion en el caso de uso
                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inavlidos o incompletos"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "Ya existe un plan con los mismos datos únicos",
                            content = @Content(schema = @Schema(implementation = ResponseError.class))
                    )

            }
    )
    @PostMapping
    public ResponseEntity<PlanResponseDTO> add(@Valid @RequestBody PLanRequestDTO pLanRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.createPlan(pLanRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(planResponseDTO);
    }



    @Operation(
            summary = "Eliminar plan",
            description = "Eliminar permanentemente un plan del sistema. La accion solo se ejecuta si el plan no tiene susbcripciones activas asociadas",


            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plan eliminado correctamente"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No existe un plan con el identificador proporcionado",
                            content = @Content(schema = @Schema(implementation = ResponseError.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Identificador inválido",
                            content = @Content(schema = @Schema(implementation = ResponseError.class))
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        planService.deletePlan(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    @Operation(
            summary = "Obtener Plan",
            description = "Recuperar la informacion detalla de un plan en especifico segun su identificador unico",


            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Plan encontrado y obtenido correctamente"
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Identificador unico de plan invalido"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Plan no encontrado segun el identificador unico proporcionado"
                    )

            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> getById(@PathVariable int id){
        PlanResponseDTO plan = planService.getPlanById(id);
        return ResponseEntity.status(HttpStatus.OK).body(plan);
    }



    @Operation(
            summary = "Actualizar e precio de un plan",
            description = "Actualizar el precio de un plan en especifico, manteniendo el resto de su informacion sin cambios",


            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Precio actualizado correctamente"
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Datos inválidos",
                            content = @Content(schema = @Schema(implementation = ResponseError.class))
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Plan no encontrado",
                            content = @Content(schema = @Schema(implementation = ResponseError.class))
                    )
            }


    )
    @PatchMapping("/{id}/price")
    public ResponseEntity<Void> changePlanPrice( @PathVariable int id,@RequestBody BigDecimal price){
        planService.updatePlanPrice(id, price);
        return ResponseEntity.status(HttpStatus.OK).build();
    }



    @Operation(
            summary = "Actualizar el estado de un plan",
            description = "Cambia el estado del plan (por ejemplo, activarlo o desactivarlo).",



            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Estado actualizado correctamente"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Formato de identificador unico del plan incorrecto"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Plan no encontrado",
                            content = @Content(schema = @Schema(implementation = ResponseError.class))
                    )
            }
    )
    @PatchMapping("/{id}/state")
    public ResponseEntity<Void> changePlanState(@PathVariable int id){
        planService.updatePlanState(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
