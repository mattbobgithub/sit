package com.sit.service.mapper;

import com.sit.domain.AlterationGroup;
import com.sit.domain.Garment;
import com.sit.service.dto.AlterationGroupDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity AlterationGroup and its DTO AlterationGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {GarmentMapper.class, })
public interface AlterationGroupMapper {

    AlterationGroupDTO alterationGroupToAlterationGroupDTO(AlterationGroup alterationGroup);

    List<AlterationGroupDTO> alterationGroupsToAlterationGroupDTOs(List<AlterationGroup> alterationGroups);

    @Mapping(target = "alterationSubGroups", ignore = true)
    AlterationGroup alterationGroupDTOToAlterationGroup(AlterationGroupDTO alterationGroupDTO);

    List<AlterationGroup> alterationGroupDTOsToAlterationGroups(List<AlterationGroupDTO> alterationGroupDTOs);

    default Garment garmentFromId(Long id) {
        if (id == null) {
            return null;
        }
        Garment garment = new Garment();
        garment.setId(id);
        return garment;
    }
}
