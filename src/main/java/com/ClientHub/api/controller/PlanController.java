package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.service.PlanService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Controller
@RequestMapping("/plan")
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponseDTO> add(@Valid @RequestBody PLanRequestDTO pLanRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.add(pLanRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(planResponseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        planService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> getById(@PathVariable int id){
        PlanResponseDTO plan = planService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(plan);
    }


    @PatchMapping("/{id}/price")
    public ResponseEntity<Void> changePlanPrice( @PathVariable int id, @NotNull @Positive @RequestBody BigDecimal price){
        planService.changePlanPrice(id, price);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PatchMapping("/{id}/state")
    public ResponseEntity<Void> changePlanState(@PathVariable int id){
        planService.modifyPlanState(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
