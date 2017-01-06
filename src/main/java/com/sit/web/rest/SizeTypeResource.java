package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.SizeTypeService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.SizeTypeDTO;

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
 * REST controller for managing SizeType.
 */
@RestController
@RequestMapping("/api")
public class SizeTypeResource {

    private final Logger log = LoggerFactory.getLogger(SizeTypeResource.class);
        
    @Inject
    private SizeTypeService sizeTypeService;

    /**
     * POST  /size-types : Create a new sizeType.
     *
     * @param sizeTypeDTO the sizeTypeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sizeTypeDTO, or with status 400 (Bad Request) if the sizeType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/size-types")
    @Timed
    public ResponseEntity<SizeTypeDTO> createSizeType(@Valid @RequestBody SizeTypeDTO sizeTypeDTO) throws URISyntaxException {
        log.debug("REST request to save SizeType : {}", sizeTypeDTO);
        if (sizeTypeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sizeType", "idexists", "A new sizeType cannot already have an ID")).body(null);
        }
        SizeTypeDTO result = sizeTypeService.save(sizeTypeDTO);
        return ResponseEntity.created(new URI("/api/size-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sizeType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /size-types : Updates an existing sizeType.
     *
     * @param sizeTypeDTO the sizeTypeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sizeTypeDTO,
     * or with status 400 (Bad Request) if the sizeTypeDTO is not valid,
     * or with status 500 (Internal Server Error) if the sizeTypeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/size-types")
    @Timed
    public ResponseEntity<SizeTypeDTO> updateSizeType(@Valid @RequestBody SizeTypeDTO sizeTypeDTO) throws URISyntaxException {
        log.debug("REST request to update SizeType : {}", sizeTypeDTO);
        if (sizeTypeDTO.getId() == null) {
            return createSizeType(sizeTypeDTO);
        }
        SizeTypeDTO result = sizeTypeService.save(sizeTypeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sizeType", sizeTypeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /size-types : get all the sizeTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sizeTypes in body
     */
    @GetMapping("/size-types")
    @Timed
    public List<SizeTypeDTO> getAllSizeTypes() {
        log.debug("REST request to get all SizeTypes");
        return sizeTypeService.findAll();
    }

    /**
     * GET  /size-types/:id : get the "id" sizeType.
     *
     * @param id the id of the sizeTypeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sizeTypeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/size-types/{id}")
    @Timed
    public ResponseEntity<SizeTypeDTO> getSizeType(@PathVariable Long id) {
        log.debug("REST request to get SizeType : {}", id);
        SizeTypeDTO sizeTypeDTO = sizeTypeService.findOne(id);
        return Optional.ofNullable(sizeTypeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /size-types/:id : delete the "id" sizeType.
     *
     * @param id the id of the sizeTypeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/size-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteSizeType(@PathVariable Long id) {
        log.debug("REST request to delete SizeType : {}", id);
        sizeTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sizeType", id.toString())).build();
    }

}
