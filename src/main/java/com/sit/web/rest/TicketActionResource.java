package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.TicketActionService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.TicketActionDTO;

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
 * REST controller for managing TicketAction.
 */
@RestController
@RequestMapping("/api")
public class TicketActionResource {

    private final Logger log = LoggerFactory.getLogger(TicketActionResource.class);
        
    @Inject
    private TicketActionService ticketActionService;

    /**
     * POST  /ticket-actions : Create a new ticketAction.
     *
     * @param ticketActionDTO the ticketActionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticketActionDTO, or with status 400 (Bad Request) if the ticketAction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ticket-actions")
    @Timed
    public ResponseEntity<TicketActionDTO> createTicketAction(@RequestBody TicketActionDTO ticketActionDTO) throws URISyntaxException {
        log.debug("REST request to save TicketAction : {}", ticketActionDTO);
        if (ticketActionDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ticketAction", "idexists", "A new ticketAction cannot already have an ID")).body(null);
        }
        TicketActionDTO result = ticketActionService.save(ticketActionDTO);
        return ResponseEntity.created(new URI("/api/ticket-actions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ticketAction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ticket-actions : Updates an existing ticketAction.
     *
     * @param ticketActionDTO the ticketActionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticketActionDTO,
     * or with status 400 (Bad Request) if the ticketActionDTO is not valid,
     * or with status 500 (Internal Server Error) if the ticketActionDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ticket-actions")
    @Timed
    public ResponseEntity<TicketActionDTO> updateTicketAction(@RequestBody TicketActionDTO ticketActionDTO) throws URISyntaxException {
        log.debug("REST request to update TicketAction : {}", ticketActionDTO);
        if (ticketActionDTO.getId() == null) {
            return createTicketAction(ticketActionDTO);
        }
        TicketActionDTO result = ticketActionService.save(ticketActionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ticketAction", ticketActionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ticket-actions : get all the ticketActions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ticketActions in body
     */
    @GetMapping("/ticket-actions")
    @Timed
    public List<TicketActionDTO> getAllTicketActions() {
        log.debug("REST request to get all TicketActions");
        return ticketActionService.findAll();
    }

    /**
     * GET  /ticket-actions/:id : get the "id" ticketAction.
     *
     * @param id the id of the ticketActionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticketActionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ticket-actions/{id}")
    @Timed
    public ResponseEntity<TicketActionDTO> getTicketAction(@PathVariable Long id) {
        log.debug("REST request to get TicketAction : {}", id);
        TicketActionDTO ticketActionDTO = ticketActionService.findOne(id);
        return Optional.ofNullable(ticketActionDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ticket-actions/:id : delete the "id" ticketAction.
     *
     * @param id the id of the ticketActionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ticket-actions/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicketAction(@PathVariable Long id) {
        log.debug("REST request to delete TicketAction : {}", id);
        ticketActionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ticketAction", id.toString())).build();
    }

}
