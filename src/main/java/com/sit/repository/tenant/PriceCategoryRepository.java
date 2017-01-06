package com.sit.repository.tenant;

import com.sit.domain.PriceCategory;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the PriceCategory entity.
 */
@SuppressWarnings("unused")
public interface PriceCategoryRepository extends JpaRepository<PriceCategory,Long> {

}
