package com.sit.service.mapper;

import com.sit.domain.Alteration;
import com.sit.domain.AlterationDisplayType;
import com.sit.domain.AlterationSubGroup;
import com.sit.service.dto.AlterationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Alteration and its DTO AlterationDTO.
 */
@Mapper(componentModel = "spring", uses = {AlterationDisplayTypeMapper.class, })
public interface AlterationMapper {

    @Mapping(source = "alterationSubGroup.id", target = "alterationSubGroupId")
    AlterationDTO alterationToAlterationDTO(Alteration alteration);

    List<AlterationDTO> alterationsToAlterationDTOs(List<Alteration> alterations);

    @Mapping(target = "alterationPrices", ignore = true)
    @Mapping(source = "alterationSubGroupId", target = "alterationSubGroup")
    Alteration alterationDTOToAlteration(AlterationDTO alterationDTO);

    List<Alteration> alterationDTOsToAlterations(List<AlterationDTO> alterationDTOs);

    default AlterationDisplayType alterationDisplayTypeFromId(Long id) {
        if (id == null) {
            return null;
        }
        AlterationDisplayType alterationDisplayType = new AlterationDisplayType();
        alterationDisplayType.setId(id);
        return alterationDisplayType;
    }

    default AlterationSubGroup alterationSubGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        AlterationSubGroup alterationSubGroup = new AlterationSubGroup();
        alterationSubGroup.setId(id);
        return alterationSubGroup;
    }
}
