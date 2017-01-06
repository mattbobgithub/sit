package com.sit.service.mapper;

import com.sit.domain.AlterationGroup;
import com.sit.domain.AlterationSubGroup;
import com.sit.service.dto.AlterationSubGroupDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity AlterationSubGroup and its DTO AlterationSubGroupDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AlterationSubGroupMapper {

    @Mapping(source = "alterationGroup.id", target = "alterationGroupId")
    AlterationSubGroupDTO alterationSubGroupToAlterationSubGroupDTO(AlterationSubGroup alterationSubGroup);

    List<AlterationSubGroupDTO> alterationSubGroupsToAlterationSubGroupDTOs(List<AlterationSubGroup> alterationSubGroups);

    @Mapping(source = "alterationGroupId", target = "alterationGroup")
    @Mapping(target = "alterations", ignore = true)
    AlterationSubGroup alterationSubGroupDTOToAlterationSubGroup(AlterationSubGroupDTO alterationSubGroupDTO);

    List<AlterationSubGroup> alterationSubGroupDTOsToAlterationSubGroups(List<AlterationSubGroupDTO> alterationSubGroupDTOs);

    default AlterationGroup alterationGroupFromId(Long id) {
        if (id == null) {
            return null;
        }
        AlterationGroup alterationGroup = new AlterationGroup();
        alterationGroup.setId(id);
        return alterationGroup;
    }
}
