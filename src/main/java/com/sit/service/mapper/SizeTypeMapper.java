package com.sit.service.mapper;

import com.sit.domain.SizeType;
import com.sit.service.dto.SizeTypeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity SizeType and its DTO SizeTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SizeTypeMapper {

    SizeTypeDTO sizeTypeToSizeTypeDTO(SizeType sizeType);

    List<SizeTypeDTO> sizeTypesToSizeTypeDTOs(List<SizeType> sizeTypes);

    @Mapping(target = "garments", ignore = true)
    @Mapping(target = "garmentSizes", ignore = true)
    SizeType sizeTypeDTOToSizeType(SizeTypeDTO sizeTypeDTO);

    List<SizeType> sizeTypeDTOsToSizeTypes(List<SizeTypeDTO> sizeTypeDTOs);
}
