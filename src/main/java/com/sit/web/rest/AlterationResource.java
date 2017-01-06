package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.AlterationService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.AlterationDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Alteration.
 */
@RestController
@RequestMapping("/api")
public class AlterationResource {

    private final Logger log = LoggerFactory.getLogger(AlterationResource.class);
        
    @Inject
    private AlterationService alterationService;

    /**
     * POST  /alterations : Create a new alteration.
     *
     * @param alterationDTO the alterationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alterationDTO, or with status 400 (Bad Request) if the alteration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alterations")
    @Timed
    public ResponseEntity<AlterationDTO> createAlteration(@Valid @RequestBody AlterationDTO alterationDTO) throws URISyntaxException {
        log.debug("REST request to save Alteration : {}", alterationDTO);
        if (alterationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alteration", "idexists", "A new alteration cannot already have an ID")).body(null);
        }
        AlterationDTO result = alterationService.save(alterationDTO);
        return ResponseEntity.created(new URI("/api/alterations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alteration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alterations : Updates an existing alteration.
     *
     * @param alterationDTO the alterationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alterationDTO,
     * or with status 400 (Bad Request) if the alterationDTO is not valid,
     * or with status 500 (Internal Server Error) if the alterationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alterations")
    @Timed
    public ResponseEntity<AlterationDTO> updateAlteration(@Valid @RequestBody AlterationDTO alterationDTO) throws URISyntaxException {
        log.debug("REST request to update Alteration : {}", alterationDTO);
        if (alterationDTO.getId() == null) {
            return createAlteration(alterationDTO);
        }
        AlterationDTO result = alterationService.save(alterationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alteration", alterationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alterations : get all the alterations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alterations in body
     */
    @GetMapping("/alterations")
    @Timed
    public List<AlterationDTO> getAllAlterations() {
        log.debug("REST request to get all Alterations");
        return alterationService.findAll();
    }

    /**
     * GET  /alterations/:id : get the "id" alteration.
     *
     * @param id the id of the alterationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alterationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alterations/{id}")
    @Timed
    public ResponseEntity<AlterationDTO> getAlteration(@PathVariable Long id) {
        log.debug("REST request to get Alteration : {}", id);
        AlterationDTO alterationDTO = alterationService.findOne(id);
        return Optional.ofNullable(alterationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alterations/:id : delete the "id" alteration.
     *
     * @param id the id of the alterationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alterations/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlteration(@PathVariable Long id) {
        log.debug("REST request to delete Alteration : {}", id);
        alterationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alteration", id.toString())).build();
    }

}
