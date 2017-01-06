package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.GarmentSizeService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.GarmentSizeDTO;

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
 * REST controller for managing GarmentSize.
 */
@RestController
@RequestMapping("/api")
public class GarmentSizeResource {

    private final Logger log = LoggerFactory.getLogger(GarmentSizeResource.class);
        
    @Inject
    private GarmentSizeService garmentSizeService;

    /**
     * POST  /garment-sizes : Create a new garmentSize.
     *
     * @param garmentSizeDTO the garmentSizeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new garmentSizeDTO, or with status 400 (Bad Request) if the garmentSize has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/garment-sizes")
    @Timed
    public ResponseEntity<GarmentSizeDTO> createGarmentSize(@Valid @RequestBody GarmentSizeDTO garmentSizeDTO) throws URISyntaxException {
        log.debug("REST request to save GarmentSize : {}", garmentSizeDTO);
        if (garmentSizeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("garmentSize", "idexists", "A new garmentSize cannot already have an ID")).body(null);
        }
        GarmentSizeDTO result = garmentSizeService.save(garmentSizeDTO);
        return ResponseEntity.created(new URI("/api/garment-sizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("garmentSize", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /garment-sizes : Updates an existing garmentSize.
     *
     * @param garmentSizeDTO the garmentSizeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated garmentSizeDTO,
     * or with status 400 (Bad Request) if the garmentSizeDTO is not valid,
     * or with status 500 (Internal Server Error) if the garmentSizeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/garment-sizes")
    @Timed
    public ResponseEntity<GarmentSizeDTO> updateGarmentSize(@Valid @RequestBody GarmentSizeDTO garmentSizeDTO) throws URISyntaxException {
        log.debug("REST request to update GarmentSize : {}", garmentSizeDTO);
        if (garmentSizeDTO.getId() == null) {
            return createGarmentSize(garmentSizeDTO);
        }
        GarmentSizeDTO result = garmentSizeService.save(garmentSizeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("garmentSize", garmentSizeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /garment-sizes : get all the garmentSizes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of garmentSizes in body
     */
    @GetMapping("/garment-sizes")
    @Timed
    public List<GarmentSizeDTO> getAllGarmentSizes() {
        log.debug("REST request to get all GarmentSizes");
        return garmentSizeService.findAll();
    }

    /**
     * GET  /garment-sizes/:id : get the "id" garmentSize.
     *
     * @param id the id of the garmentSizeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the garmentSizeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/garment-sizes/{id}")
    @Timed
    public ResponseEntity<GarmentSizeDTO> getGarmentSize(@PathVariable Long id) {
        log.debug("REST request to get GarmentSize : {}", id);
        GarmentSizeDTO garmentSizeDTO = garmentSizeService.findOne(id);
        return Optional.ofNullable(garmentSizeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /garment-sizes/:id : delete the "id" garmentSize.
     *
     * @param id the id of the garmentSizeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/garment-sizes/{id}")
    @Timed
    public ResponseEntity<Void> deleteGarmentSize(@PathVariable Long id) {
        log.debug("REST request to delete GarmentSize : {}", id);
        garmentSizeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("garmentSize", id.toString())).build();
    }

}
