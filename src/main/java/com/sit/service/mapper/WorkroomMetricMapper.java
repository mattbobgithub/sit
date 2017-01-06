package com.sit.service.mapper;

import com.sit.domain.WorkroomMetric;
import com.sit.service.dto.WorkroomMetricDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity WorkroomMetric and its DTO WorkroomMetricDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface WorkroomMetricMapper {

    WorkroomMetricDTO workroomMetricToWorkroomMetricDTO(WorkroomMetric workroomMetric);

    List<WorkroomMetricDTO> workroomMetricsToWorkroomMetricDTOs(List<WorkroomMetric> workroomMetrics);

    WorkroomMetric workroomMetricDTOToWorkroomMetric(WorkroomMetricDTO workroomMetricDTO);

    List<WorkroomMetric> workroomMetricDTOsToWorkroomMetrics(List<WorkroomMetricDTO> workroomMetricDTOs);
}
