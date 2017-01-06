package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.WorkroomService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.WorkroomDTO;

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
 * REST controller for managing Workroom.
 */
@RestController
@RequestMapping("/api")
public class WorkroomResource {

    private final Logger log = LoggerFactory.getLogger(WorkroomResource.class);
        
    @Inject
    private WorkroomService workroomService;

    /**
     * POST  /workrooms : Create a new workroom.
     *
     * @param workroomDTO the workroomDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workroomDTO, or with status 400 (Bad Request) if the workroom has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workrooms")
    @Timed
    public ResponseEntity<WorkroomDTO> createWorkroom(@Valid @RequestBody WorkroomDTO workroomDTO) throws URISyntaxException {
        log.debug("REST request to save Workroom : {}", workroomDTO);
        if (workroomDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workroom", "idexists", "A new workroom cannot already have an ID")).body(null);
        }
        WorkroomDTO result = workroomService.save(workroomDTO);
        return ResponseEntity.created(new URI("/api/workrooms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workroom", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workrooms : Updates an existing workroom.
     *
     * @param workroomDTO the workroomDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workroomDTO,
     * or with status 400 (Bad Request) if the workroomDTO is not valid,
     * or with status 500 (Internal Server Error) if the workroomDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workrooms")
    @Timed
    public ResponseEntity<WorkroomDTO> updateWorkroom(@Valid @RequestBody WorkroomDTO workroomDTO) throws URISyntaxException {
        log.debug("REST request to update Workroom : {}", workroomDTO);
        if (workroomDTO.getId() == null) {
            return createWorkroom(workroomDTO);
        }
        WorkroomDTO result = workroomService.save(workroomDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workroom", workroomDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workrooms : get all the workrooms.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workrooms in body
     */
    @GetMapping("/workrooms")
    @Timed
    public List<WorkroomDTO> getAllWorkrooms() {
        log.debug("REST request to get all Workrooms");
        return workroomService.findAll();
    }

    /**
     * GET  /workrooms/:id : get the "id" workroom.
     *
     * @param id the id of the workroomDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workroomDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workrooms/{id}")
    @Timed
    public ResponseEntity<WorkroomDTO> getWorkroom(@PathVariable Long id) {
        log.debug("REST request to get Workroom : {}", id);
        WorkroomDTO workroomDTO = workroomService.findOne(id);
        return Optional.ofNullable(workroomDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /workrooms/:id : delete the "id" workroom.
     *
     * @param id the id of the workroomDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workrooms/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkroom(@PathVariable Long id) {
        log.debug("REST request to delete Workroom : {}", id);
        workroomService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workroom", id.toString())).build();
    }

}
