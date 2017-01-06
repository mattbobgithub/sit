package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.TicketService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.TicketDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Ticket.
 */
@RestController
@RequestMapping("/api")
public class TicketResource {

    private final Logger log = LoggerFactory.getLogger(TicketResource.class);
        
    @Inject
    private TicketService ticketService;

    /**
     * POST  /tickets : Create a new ticket.
     *
     * @param ticketDTO the ticketDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticketDTO, or with status 400 (Bad Request) if the ticket has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tickets")
    @Timed
    public ResponseEntity<TicketDTO> createTicket(@RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to save Ticket : {}", ticketDTO);
        if (ticketDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ticket", "idexists", "A new ticket cannot already have an ID")).body(null);
        }
        TicketDTO result = ticketService.save(ticketDTO);
        return ResponseEntity.created(new URI("/api/tickets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ticket", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tickets : Updates an existing ticket.
     *
     * @param ticketDTO the ticketDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticketDTO,
     * or with status 400 (Bad Request) if the ticketDTO is not valid,
     * or with status 500 (Internal Server Error) if the ticketDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tickets")
    @Timed
    public ResponseEntity<TicketDTO> updateTicket(@RequestBody TicketDTO ticketDTO) throws URISyntaxException {
        log.debug("REST request to update Ticket : {}", ticketDTO);
        if (ticketDTO.getId() == null) {
            return createTicket(ticketDTO);
        }
        TicketDTO result = ticketService.save(ticketDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ticket", ticketDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tickets : get all the tickets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tickets in body
     */
    @GetMapping("/tickets")
    @Timed
    public List<TicketDTO> getAllTickets() {
        log.debug("REST request to get all Tickets");
        return ticketService.findAll();
    }

    /**
     * GET  /tickets/:id : get the "id" ticket.
     *
     * @param id the id of the ticketDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticketDTO, or with status 404 (Not Found)
     */
    @GetMapping("/tickets/{id}")
    @Timed
    public ResponseEntity<TicketDTO> getTicket(@PathVariable Long id) {
        log.debug("REST request to get Ticket : {}", id);
        TicketDTO ticketDTO = ticketService.findOne(id);
        return Optional.ofNullable(ticketDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tickets/:id : delete the "id" ticket.
     *
     * @param id the id of the ticketDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tickets/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicket(@PathVariable Long id) {
        log.debug("REST request to delete Ticket : {}", id);
        ticketService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ticket", id.toString())).build();
    }

}
