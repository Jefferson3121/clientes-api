package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;
import com.ClientHub.api.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/client")
@Tag(name = "Client", description = "Operaciones crud relacionadas con Client")
public class ClientController {

    private final ClientService clientService;


    @Operation(
            summary = "Crear un nuevo cliente",
            description = "Crear y registrar un nuevo cliente en el sistema"
    )

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "409", description = "Cliente ya existe"),
            @ApiResponse(responseCode = "400", description = "peticion incorrecta")
    })

    @PostMapping
    public ResponseEntity<Void> registerClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO){

        clientService.registerClient(clientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



@Operation(
        summary = "Obtener un cliente",
        description = "Obtener un cliente por su id"
)


@ApiResponses(
        @ApiResponse(responseCode = "200", description = "Cuerpo de Client en formato Json")
)

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> getByIdClient(@PathVariable int id){

        ClientResponseDTO responseDTO = clientService.getByIdClient(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

    }

    @PatchMapping("/{id}/name")
    public ResponseEntity<Void> updateClientName(@PathVariable int id, @Valid @RequestBody   ClientRequestChangeNameDTO clientRequestChangeNameDTO){

        clientService.updateClientName(id, clientRequestChangeNameDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/{id}/email")
    public ResponseEntity<Void> updateClientEmail(@PathVariable int id, @Valid @RequestBody ClientRequestChangeEmailDTO changeEmailDTO){

        clientService.updateClientEmail(id,changeEmailDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PatchMapping("/{id}/state")
    public ResponseEntity<Void> updateClientState(@PathVariable int id){
        validateId(id); // revisar aqui de que pasa cuando es int primitivo y no se le envia valor ya que no podemos validar null
        clientService.updateClientState(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }



    private void validateId(Integer id){
        Objects.requireNonNull(id, "El id no puede ser null");
    }
}
