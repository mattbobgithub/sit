package com.sit.service.mapper;

import com.sit.domain.Workroom;
import com.sit.domain.WorkroomMetric;
import com.sit.service.dto.WorkroomDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Workroom and its DTO WorkroomDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkroomMapper {

    @Mapping(source = "workroomMetric.id", target = "workroomMetricId")
    WorkroomDTO workroomToWorkroomDTO(Workroom workroom);

    List<WorkroomDTO> workroomsToWorkroomDTOs(List<Workroom> workrooms);

    @Mapping(source = "workroomMetricId", target = "workroomMetric")
    @Mapping(target = "workroomUsers", ignore = true)
    Workroom workroomDTOToWorkroom(WorkroomDTO workroomDTO);

    List<Workroom> workroomDTOsToWorkrooms(List<WorkroomDTO> workroomDTOs);

    default WorkroomMetric workroomMetricFromId(Long id) {
        if (id == null) {
            return null;
        }
        WorkroomMetric workroomMetric = new WorkroomMetric();
        workroomMetric.setId(id);
        return workroomMetric;
    }
}
