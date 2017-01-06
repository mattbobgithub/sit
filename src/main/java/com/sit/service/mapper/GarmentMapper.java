package com.sit.service.mapper;

import com.sit.domain.Garment;
import com.sit.domain.SizeType;
import com.sit.service.dto.GarmentDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Garment and its DTO GarmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GarmentMapper {

    @Mapping(source = "sizeType.id", target = "sizeTypeId")
    GarmentDTO garmentToGarmentDTO(Garment garment);

    List<GarmentDTO> garmentsToGarmentDTOs(List<Garment> garments);

    @Mapping(target = "alterationGroups", ignore = true)
    @Mapping(source = "sizeTypeId", target = "sizeType")
    Garment garmentDTOToGarment(GarmentDTO garmentDTO);

    List<Garment> garmentDTOsToGarments(List<GarmentDTO> garmentDTOs);

    default SizeType sizeTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        SizeType sizeType = new SizeType();
        sizeType.setId(id);
        return sizeType;
    }
}
