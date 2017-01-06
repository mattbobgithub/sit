package com.sit.repository.tenant;

import com.sit.domain.TicketAlteration;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the TicketAlteration entity.
 */
@SuppressWarnings("unused")
public interface TicketAlterationRepository extends JpaRepository<TicketAlteration,Long> {

}
