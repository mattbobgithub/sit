package com.sit.service.mapper;

import com.sit.domain.AlterationDisplayType;
import com.sit.service.dto.AlterationDisplayTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity AlterationDisplayType and its DTO AlterationDisplayTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlterationDisplayTypeMapper {

    AlterationDisplayTypeDTO alterationDisplayTypeToAlterationDisplayTypeDTO(AlterationDisplayType alterationDisplayType);

    List<AlterationDisplayTypeDTO> alterationDisplayTypesToAlterationDisplayTypeDTOs(List<AlterationDisplayType> alterationDisplayTypes);

    @Mapping(target = "alterations", ignore = true)
    AlterationDisplayType alterationDisplayTypeDTOToAlterationDisplayType(AlterationDisplayTypeDTO alterationDisplayTypeDTO);

    List<AlterationDisplayType> alterationDisplayTypeDTOsToAlterationDisplayTypes(List<AlterationDisplayTypeDTO> alterationDisplayTypeDTOs);
}
