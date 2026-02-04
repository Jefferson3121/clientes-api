package com.ClientHub.api.service;

import com.ClientHub.api.component.PlanMapper;
import com.ClientHub.api.dto.request.PLanRequestDTO;
import com.ClientHub.api.dto.response.PlanResponseDTO;
import com.ClientHub.api.exception.PlanNoFoundException;
import com.ClientHub.api.exception.UnchangedValueException;
import com.ClientHub.api.domain.Plan;
import com.ClientHub.api.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class PlanImplService implements PlanService {

    private final PlanRepository planRepository;
    private final PlanMapper planMapper;

    public static final Logger logger = LoggerFactory.getLogger(PlanImplService.class);

    @Transactional
    @Override
    public PlanResponseDTO add(PLanRequestDTO pLanRequestDTO) {

        Plan plan = planMapper.toPlan(pLanRequestDTO);
        Plan planResponse = planRepository.save(plan);
        return planMapper.toPlanResponseDTO(planResponse);
    }



    @Transactional
    @Override
    public void delete(int id){

        validateId(id);

        Plan plan = planRepository.findById(id)
                        .orElseThrow(()-> new PlanNoFoundException("No existe el plan que intenta eliminar "));

        planRepository.delete(plan);
    }

    @Transactional(readOnly = true)
    @Override
    public PlanResponseDTO getById(int id){
        validateId(id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(()-> new PlanNoFoundException("Plan con id " + id + "no existe"));

        logger.info("______ {}", plan.getDuration());
        logger.info("_____ {}",plan.getName() );

        return planMapper.toPlanResponseDTO(plan);
    }


    @Transactional
    @Override
    public void changePlanName(int id, String newName) {

        validateId(id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(() -> new PlanNoFoundException("No existe el plan que intenta modificar"));


        if (newName.equals(plan.getName())) {
            throw new UnchangedValueException("Nuevo name es igual a name actual");
        }

        plan.modifyName(newName);
        planRepository.save(plan);
    }


    @Transactional
    @Override
    public void changePlanPrice(int id,BigDecimal price){

        if (price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Price no puede ser inferior o igual a cero (0)");
        }

        Plan plan = planRepository.findById(id)
                .orElseThrow(()-> new PlanNoFoundException("Plan con id " + id + "no existe"));

        plan.modifyPrice(price);
        planRepository.save(plan);
    }



    @Transactional
    @Override
    public void modifyPlanState(int id){
        validateId(id);

        Plan plan = planRepository.findById(id)
                .orElseThrow(()-> new PlanNoFoundException("Plan con id " + id + "no existe"));

        plan.modifyState();
        planRepository.save(plan);
    }




    private void validateId(Integer id){

        if (id == null || id < 0){
            throw new IllegalArgumentException("Formato o valor de id incorrecto");
        }
    }

}
