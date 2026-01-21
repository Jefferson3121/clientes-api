package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.ClientRequestChangeEmailDTO;
import com.ClientHub.api.dto.request.ClientRequestChangeNameDTO;
import com.ClientHub.api.dto.request.ClientRequestDTO;
import com.ClientHub.api.dto.response.ClientResponseDTO;
import com.ClientHub.api.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping("/client")
public class ClientController {

    private final ClientService clientService;


    @PostMapping
    public ResponseEntity<Void> registerClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO){

        clientService.registerClient(clientRequestDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

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
