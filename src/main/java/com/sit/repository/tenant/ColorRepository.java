package com.sit.repository.tenant;

import com.sit.domain.Color;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Color entity.
 */
@SuppressWarnings("unused")
public interface ColorRepository extends JpaRepository<Color,Long> {

}
