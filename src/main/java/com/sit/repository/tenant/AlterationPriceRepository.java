package com.sit.repository.tenant;

import com.sit.domain.AlterationPrice;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the AlterationPrice entity.
 */
@SuppressWarnings("unused")
public interface AlterationPriceRepository extends JpaRepository<AlterationPrice,Long> {

}
