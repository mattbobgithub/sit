package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.TicketAlterationService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.TicketAlterationDTO;

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
 * REST controller for managing TicketAlteration.
 */
@RestController
@RequestMapping("/api")
public class TicketAlterationResource {

    private final Logger log = LoggerFactory.getLogger(TicketAlterationResource.class);
        
    @Inject
    private TicketAlterationService ticketAlterationService;

    /**
     * POST  /ticket-alterations : Create a new ticketAlteration.
     *
     * @param ticketAlterationDTO the ticketAlterationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ticketAlterationDTO, or with status 400 (Bad Request) if the ticketAlteration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ticket-alterations")
    @Timed
    public ResponseEntity<TicketAlterationDTO> createTicketAlteration(@RequestBody TicketAlterationDTO ticketAlterationDTO) throws URISyntaxException {
        log.debug("REST request to save TicketAlteration : {}", ticketAlterationDTO);
        if (ticketAlterationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("ticketAlteration", "idexists", "A new ticketAlteration cannot already have an ID")).body(null);
        }
        TicketAlterationDTO result = ticketAlterationService.save(ticketAlterationDTO);
        return ResponseEntity.created(new URI("/api/ticket-alterations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("ticketAlteration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ticket-alterations : Updates an existing ticketAlteration.
     *
     * @param ticketAlterationDTO the ticketAlterationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ticketAlterationDTO,
     * or with status 400 (Bad Request) if the ticketAlterationDTO is not valid,
     * or with status 500 (Internal Server Error) if the ticketAlterationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ticket-alterations")
    @Timed
    public ResponseEntity<TicketAlterationDTO> updateTicketAlteration(@RequestBody TicketAlterationDTO ticketAlterationDTO) throws URISyntaxException {
        log.debug("REST request to update TicketAlteration : {}", ticketAlterationDTO);
        if (ticketAlterationDTO.getId() == null) {
            return createTicketAlteration(ticketAlterationDTO);
        }
        TicketAlterationDTO result = ticketAlterationService.save(ticketAlterationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("ticketAlteration", ticketAlterationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ticket-alterations : get all the ticketAlterations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ticketAlterations in body
     */
    @GetMapping("/ticket-alterations")
    @Timed
    public List<TicketAlterationDTO> getAllTicketAlterations() {
        log.debug("REST request to get all TicketAlterations");
        return ticketAlterationService.findAll();
    }

    /**
     * GET  /ticket-alterations/:id : get the "id" ticketAlteration.
     *
     * @param id the id of the ticketAlterationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ticketAlterationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/ticket-alterations/{id}")
    @Timed
    public ResponseEntity<TicketAlterationDTO> getTicketAlteration(@PathVariable Long id) {
        log.debug("REST request to get TicketAlteration : {}", id);
        TicketAlterationDTO ticketAlterationDTO = ticketAlterationService.findOne(id);
        return Optional.ofNullable(ticketAlterationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /ticket-alterations/:id : delete the "id" ticketAlteration.
     *
     * @param id the id of the ticketAlterationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ticket-alterations/{id}")
    @Timed
    public ResponseEntity<Void> deleteTicketAlteration(@PathVariable Long id) {
        log.debug("REST request to delete TicketAlteration : {}", id);
        ticketAlterationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("ticketAlteration", id.toString())).build();
    }

}
