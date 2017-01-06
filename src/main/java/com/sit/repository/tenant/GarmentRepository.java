package com.sit.repository.tenant;

import com.sit.domain.Garment;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Garment entity.
 */
@SuppressWarnings("unused")
public interface GarmentRepository extends JpaRepository<Garment,Long> {

}
