package com.sit.repository.tenant;

import com.sit.domain.Ticket;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Ticket entity.
 */
@SuppressWarnings("unused")
public interface TicketRepository extends JpaRepository<Ticket,Long> {

}
