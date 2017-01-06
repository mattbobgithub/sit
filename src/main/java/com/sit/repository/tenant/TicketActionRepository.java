package com.sit.repository.tenant;

import com.sit.domain.TicketAction;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the TicketAction entity.
 */
@SuppressWarnings("unused")
public interface TicketActionRepository extends JpaRepository<TicketAction,Long> {

}
