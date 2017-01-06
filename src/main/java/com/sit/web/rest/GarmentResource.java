package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.GarmentService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.GarmentDTO;

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
 * REST controller for managing Garment.
 */
@RestController
@RequestMapping("/api")
public class GarmentResource {

    private final Logger log = LoggerFactory.getLogger(GarmentResource.class);
        
    @Inject
    private GarmentService garmentService;

    /**
     * POST  /garments : Create a new garment.
     *
     * @param garmentDTO the garmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new garmentDTO, or with status 400 (Bad Request) if the garment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/garments")
    @Timed
    public ResponseEntity<GarmentDTO> createGarment(@Valid @RequestBody GarmentDTO garmentDTO) throws URISyntaxException {
        log.debug("REST request to save Garment : {}", garmentDTO);
        if (garmentDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("garment", "idexists", "A new garment cannot already have an ID")).body(null);
        }
        GarmentDTO result = garmentService.save(garmentDTO);
        return ResponseEntity.created(new URI("/api/garments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("garment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /garments : Updates an existing garment.
     *
     * @param garmentDTO the garmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated garmentDTO,
     * or with status 400 (Bad Request) if the garmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the garmentDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/garments")
    @Timed
    public ResponseEntity<GarmentDTO> updateGarment(@Valid @RequestBody GarmentDTO garmentDTO) throws URISyntaxException {
        log.debug("REST request to update Garment : {}", garmentDTO);
        if (garmentDTO.getId() == null) {
            return createGarment(garmentDTO);
        }
        GarmentDTO result = garmentService.save(garmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("garment", garmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /garments : get all the garments.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of garments in body
     */
    @GetMapping("/garments")
    @Timed
    public List<GarmentDTO> getAllGarments() {
        log.debug("REST request to get all Garments");
        return garmentService.findAll();
    }

    /**
     * GET  /garments/:id : get the "id" garment.
     *
     * @param id the id of the garmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the garmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/garments/{id}")
    @Timed
    public ResponseEntity<GarmentDTO> getGarment(@PathVariable Long id) {
        log.debug("REST request to get Garment : {}", id);
        GarmentDTO garmentDTO = garmentService.findOne(id);
        return Optional.ofNullable(garmentDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /garments/:id : delete the "id" garment.
     *
     * @param id the id of the garmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/garments/{id}")
    @Timed
    public ResponseEntity<Void> deleteGarment(@PathVariable Long id) {
        log.debug("REST request to delete Garment : {}", id);
        garmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("garment", id.toString())).build();
    }

}
