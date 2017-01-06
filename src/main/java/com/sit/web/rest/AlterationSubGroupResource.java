package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.AlterationSubGroupService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.AlterationSubGroupDTO;

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
 * REST controller for managing AlterationSubGroup.
 */
@RestController
@RequestMapping("/api")
public class AlterationSubGroupResource {

    private final Logger log = LoggerFactory.getLogger(AlterationSubGroupResource.class);
        
    @Inject
    private AlterationSubGroupService alterationSubGroupService;

    /**
     * POST  /alteration-sub-groups : Create a new alterationSubGroup.
     *
     * @param alterationSubGroupDTO the alterationSubGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alterationSubGroupDTO, or with status 400 (Bad Request) if the alterationSubGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alteration-sub-groups")
    @Timed
    public ResponseEntity<AlterationSubGroupDTO> createAlterationSubGroup(@Valid @RequestBody AlterationSubGroupDTO alterationSubGroupDTO) throws URISyntaxException {
        log.debug("REST request to save AlterationSubGroup : {}", alterationSubGroupDTO);
        if (alterationSubGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alterationSubGroup", "idexists", "A new alterationSubGroup cannot already have an ID")).body(null);
        }
        AlterationSubGroupDTO result = alterationSubGroupService.save(alterationSubGroupDTO);
        return ResponseEntity.created(new URI("/api/alteration-sub-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alterationSubGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alteration-sub-groups : Updates an existing alterationSubGroup.
     *
     * @param alterationSubGroupDTO the alterationSubGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alterationSubGroupDTO,
     * or with status 400 (Bad Request) if the alterationSubGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the alterationSubGroupDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alteration-sub-groups")
    @Timed
    public ResponseEntity<AlterationSubGroupDTO> updateAlterationSubGroup(@Valid @RequestBody AlterationSubGroupDTO alterationSubGroupDTO) throws URISyntaxException {
        log.debug("REST request to update AlterationSubGroup : {}", alterationSubGroupDTO);
        if (alterationSubGroupDTO.getId() == null) {
            return createAlterationSubGroup(alterationSubGroupDTO);
        }
        AlterationSubGroupDTO result = alterationSubGroupService.save(alterationSubGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alterationSubGroup", alterationSubGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alteration-sub-groups : get all the alterationSubGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alterationSubGroups in body
     */
    @GetMapping("/alteration-sub-groups")
    @Timed
    public List<AlterationSubGroupDTO> getAllAlterationSubGroups() {
        log.debug("REST request to get all AlterationSubGroups");
        return alterationSubGroupService.findAll();
    }

    /**
     * GET  /alteration-sub-groups/:id : get the "id" alterationSubGroup.
     *
     * @param id the id of the alterationSubGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alterationSubGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alteration-sub-groups/{id}")
    @Timed
    public ResponseEntity<AlterationSubGroupDTO> getAlterationSubGroup(@PathVariable Long id) {
        log.debug("REST request to get AlterationSubGroup : {}", id);
        AlterationSubGroupDTO alterationSubGroupDTO = alterationSubGroupService.findOne(id);
        return Optional.ofNullable(alterationSubGroupDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alteration-sub-groups/:id : delete the "id" alterationSubGroup.
     *
     * @param id the id of the alterationSubGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alteration-sub-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlterationSubGroup(@PathVariable Long id) {
        log.debug("REST request to delete AlterationSubGroup : {}", id);
        alterationSubGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alterationSubGroup", id.toString())).build();
    }

}
