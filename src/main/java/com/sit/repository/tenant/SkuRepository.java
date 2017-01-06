package com.sit.repository.tenant;

import com.sit.domain.Sku;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Sku entity.
 */
@SuppressWarnings("unused")
public interface SkuRepository extends JpaRepository<Sku,Long> {

}
