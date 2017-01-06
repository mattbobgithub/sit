package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.AlterationDisplayTypeService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.AlterationDisplayTypeDTO;

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
 * REST controller for managing AlterationDisplayType.
 */
@RestController
@RequestMapping("/api")
public class AlterationDisplayTypeResource {

    private final Logger log = LoggerFactory.getLogger(AlterationDisplayTypeResource.class);
        
    @Inject
    private AlterationDisplayTypeService alterationDisplayTypeService;

    /**
     * POST  /alteration-display-types : Create a new alterationDisplayType.
     *
     * @param alterationDisplayTypeDTO the alterationDisplayTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alterationDisplayTypeDTO, or with status 400 (Bad Request) if the alterationDisplayType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alteration-display-types")
    @Timed
    public ResponseEntity<AlterationDisplayTypeDTO> createAlterationDisplayType(@Valid @RequestBody AlterationDisplayTypeDTO alterationDisplayTypeDTO) throws URISyntaxException {
        log.debug("REST request to save AlterationDisplayType : {}", alterationDisplayTypeDTO);
        if (alterationDisplayTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alterationDisplayType", "idexists", "A new alterationDisplayType cannot already have an ID")).body(null);
        }
        AlterationDisplayTypeDTO result = alterationDisplayTypeService.save(alterationDisplayTypeDTO);
        return ResponseEntity.created(new URI("/api/alteration-display-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alterationDisplayType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alteration-display-types : Updates an existing alterationDisplayType.
     *
     * @param alterationDisplayTypeDTO the alterationDisplayTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alterationDisplayTypeDTO,
     * or with status 400 (Bad Request) if the alterationDisplayTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the alterationDisplayTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alteration-display-types")
    @Timed
    public ResponseEntity<AlterationDisplayTypeDTO> updateAlterationDisplayType(@Valid @RequestBody AlterationDisplayTypeDTO alterationDisplayTypeDTO) throws URISyntaxException {
        log.debug("REST request to update AlterationDisplayType : {}", alterationDisplayTypeDTO);
        if (alterationDisplayTypeDTO.getId() == null) {
            return createAlterationDisplayType(alterationDisplayTypeDTO);
        }
        AlterationDisplayTypeDTO result = alterationDisplayTypeService.save(alterationDisplayTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alterationDisplayType", alterationDisplayTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alteration-display-types : get all the alterationDisplayTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alterationDisplayTypes in body
     */
    @GetMapping("/alteration-display-types")
    @Timed
    public List<AlterationDisplayTypeDTO> getAllAlterationDisplayTypes() {
        log.debug("REST request to get all AlterationDisplayTypes");
        return alterationDisplayTypeService.findAll();
    }

    /**
     * GET  /alteration-display-types/:id : get the "id" alterationDisplayType.
     *
     * @param id the id of the alterationDisplayTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alterationDisplayTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alteration-display-types/{id}")
    @Timed
    public ResponseEntity<AlterationDisplayTypeDTO> getAlterationDisplayType(@PathVariable Long id) {
        log.debug("REST request to get AlterationDisplayType : {}", id);
        AlterationDisplayTypeDTO alterationDisplayTypeDTO = alterationDisplayTypeService.findOne(id);
        return Optional.ofNullable(alterationDisplayTypeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alteration-display-types/:id : delete the "id" alterationDisplayType.
     *
     * @param id the id of the alterationDisplayTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alteration-display-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlterationDisplayType(@PathVariable Long id) {
        log.debug("REST request to delete AlterationDisplayType : {}", id);
        alterationDisplayTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alterationDisplayType", id.toString())).build();
    }

}
