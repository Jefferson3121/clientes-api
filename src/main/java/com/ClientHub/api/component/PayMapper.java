package com.ClientHub.api.component;

import com.ClientHub.api.dto.response.PayResponseDTO;
import com.ClientHub.api.domain.Pay;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",
                         injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface PayMapper {

    @Mapping(source = "suscription.id", target = "suscriptionId")
    PayResponseDTO toPayResponseDTO(Pay pay);


}
