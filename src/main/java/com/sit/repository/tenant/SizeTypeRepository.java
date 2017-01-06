package com.sit.repository.tenant;

import com.sit.domain.SizeType;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the SizeType entity.
 */
@SuppressWarnings("unused")
public interface SizeTypeRepository extends JpaRepository<SizeType,Long> {

}
