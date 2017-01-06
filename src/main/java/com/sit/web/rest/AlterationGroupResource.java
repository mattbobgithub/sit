package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.AlterationGroupService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.AlterationGroupDTO;

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
 * REST controller for managing AlterationGroup.
 */
@RestController
@RequestMapping("/api")
public class AlterationGroupResource {

    private final Logger log = LoggerFactory.getLogger(AlterationGroupResource.class);
        
    @Inject
    private AlterationGroupService alterationGroupService;

    /**
     * POST  /alteration-groups : Create a new alterationGroup.
     *
     * @param alterationGroupDTO the alterationGroupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alterationGroupDTO, or with status 400 (Bad Request) if the alterationGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alteration-groups")
    @Timed
    public ResponseEntity<AlterationGroupDTO> createAlterationGroup(@Valid @RequestBody AlterationGroupDTO alterationGroupDTO) throws URISyntaxException {
        log.debug("REST request to save AlterationGroup : {}", alterationGroupDTO);
        if (alterationGroupDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alterationGroup", "idexists", "A new alterationGroup cannot already have an ID")).body(null);
        }
        AlterationGroupDTO result = alterationGroupService.save(alterationGroupDTO);
        return ResponseEntity.created(new URI("/api/alteration-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alterationGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alteration-groups : Updates an existing alterationGroup.
     *
     * @param alterationGroupDTO the alterationGroupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alterationGroupDTO,
     * or with status 400 (Bad Request) if the alterationGroupDTO is not valid,
     * or with status 500 (Internal Server Error) if the alterationGroupDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alteration-groups")
    @Timed
    public ResponseEntity<AlterationGroupDTO> updateAlterationGroup(@Valid @RequestBody AlterationGroupDTO alterationGroupDTO) throws URISyntaxException {
        log.debug("REST request to update AlterationGroup : {}", alterationGroupDTO);
        if (alterationGroupDTO.getId() == null) {
            return createAlterationGroup(alterationGroupDTO);
        }
        AlterationGroupDTO result = alterationGroupService.save(alterationGroupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alterationGroup", alterationGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alteration-groups : get all the alterationGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alterationGroups in body
     */
    @GetMapping("/alteration-groups")
    @Timed
    public List<AlterationGroupDTO> getAllAlterationGroups() {
        log.debug("REST request to get all AlterationGroups");
        return alterationGroupService.findAll();
    }

    /**
     * GET  /alteration-groups/:id : get the "id" alterationGroup.
     *
     * @param id the id of the alterationGroupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alterationGroupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alteration-groups/{id}")
    @Timed
    public ResponseEntity<AlterationGroupDTO> getAlterationGroup(@PathVariable Long id) {
        log.debug("REST request to get AlterationGroup : {}", id);
        AlterationGroupDTO alterationGroupDTO = alterationGroupService.findOne(id);
        return Optional.ofNullable(alterationGroupDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alteration-groups/:id : delete the "id" alterationGroup.
     *
     * @param id the id of the alterationGroupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alteration-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlterationGroup(@PathVariable Long id) {
        log.debug("REST request to delete AlterationGroup : {}", id);
        alterationGroupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alterationGroup", id.toString())).build();
    }

}
