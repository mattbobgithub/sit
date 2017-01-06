package com.sit.service.mapper;

import com.sit.domain.GarmentSize;
import com.sit.domain.SizeType;
import com.sit.service.dto.GarmentSizeDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity GarmentSize and its DTO GarmentSizeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GarmentSizeMapper {

    @Mapping(source = "sizeType.id", target = "sizeTypeId")
    GarmentSizeDTO garmentSizeToGarmentSizeDTO(GarmentSize garmentSize);

    List<GarmentSizeDTO> garmentSizesToGarmentSizeDTOs(List<GarmentSize> garmentSizes);

    @Mapping(source = "sizeTypeId", target = "sizeType")
    GarmentSize garmentSizeDTOToGarmentSize(GarmentSizeDTO garmentSizeDTO);

    List<GarmentSize> garmentSizeDTOsToGarmentSizes(List<GarmentSizeDTO> garmentSizeDTOs);

    default SizeType sizeTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        SizeType sizeType = new SizeType();
        sizeType.setId(id);
        return sizeType;
    }
}
