package com.ClientHub.api.controller;

import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.service.PlanService;
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
public class PlanController {

    private final PlanService planService;

    @PostMapping
    public ResponseEntity<PlanResponseDTO> add(@Valid @RequestBody PLanRequestDTO pLanRequestDTO) {
        PlanResponseDTO planResponseDTO = planService.createPlan(pLanRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(planResponseDTO);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id){
        planService.deletePlan(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> getById(@PathVariable int id){
        PlanResponseDTO plan = planService.getPlanById(id);
        return ResponseEntity.status(HttpStatus.OK).body(plan);
    }


    @PatchMapping("/{id}/price")
    public ResponseEntity<Void> changePlanPrice( @PathVariable int id, @NotNull @Positive @RequestBody BigDecimal price){
        planService.updatePlanPrice(id, price);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @PatchMapping("/{id}/state")
    public ResponseEntity<Void> changePlanState(@PathVariable int id){
        planService.updatePlanState(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
