package com.sit.repository.tenant;

import com.sit.domain.Workroom;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Workroom entity.
 */
@SuppressWarnings("unused")
public interface WorkroomRepository extends JpaRepository<Workroom,Long> {

}
