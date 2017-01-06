package com.sit.repository.tenant;

import com.sit.domain.GarmentSize;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the GarmentSize entity.
 */
@SuppressWarnings("unused")
public interface GarmentSizeRepository extends JpaRepository<GarmentSize,Long> {

}
