package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.CreatePayRequestDTO;
import com.ClientHub.api.dto.response.PayResponseDTO;
import com.ClientHub.api.service.contrat.PayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pay")
@RequiredArgsConstructor
@Tag(name = "Pay", description = "Operaciones relacionadas con pagos")
public class PayController {

    private final PayService payService;

    @Operation(
            summary = "Registrar un pago",
            description = "Registra un nuevo pago asociado a una suscripción existente",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Pago registrado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos o incompletos"),
                    @ApiResponse(responseCode = "404", description = "Suscripción no encontrada")
            }
    )
    @PostMapping
    public ResponseEntity<PayResponseDTO> createPay(@Valid @RequestBody CreatePayRequestDTO createPayRequestDTO) {
        PayResponseDTO response = payService.createPay(createPayRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(
            summary = "Obtener un pago",
            description = "Obtiene la información de un pago según su identificador único",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pago encontrado correctamente"),
                    @ApiResponse(responseCode = "400", description = "Tipo de dato del is invalido"),
                    @ApiResponse(responseCode = "404", description = "Pago no encontrado")
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PayResponseDTO> getPay(@PathVariable Integer id) {
        PayResponseDTO response = payService.getPay(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
