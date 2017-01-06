package com.sit.repository.tenant;

import com.sit.domain.WorkroomMetric;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the WorkroomMetric entity.
 */
@SuppressWarnings("unused")
public interface WorkroomMetricRepository extends JpaRepository<WorkroomMetric,Long> {

}
