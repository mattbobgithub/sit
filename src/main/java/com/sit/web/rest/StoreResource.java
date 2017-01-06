package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.StoreService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.StoreDTO;

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
 * REST controller for managing Store.
 */
@RestController
@RequestMapping("/api")
public class StoreResource {

    private final Logger log = LoggerFactory.getLogger(StoreResource.class);
        
    @Inject
    private StoreService storeService;

    /**
     * POST  /stores : Create a new store.
     *
     * @param storeDTO the storeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new storeDTO, or with status 400 (Bad Request) if the store has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/stores")
    @Timed
    public ResponseEntity<StoreDTO> createStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to save Store : {}", storeDTO);
        if (storeDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("store", "idexists", "A new store cannot already have an ID")).body(null);
        }
        StoreDTO result = storeService.save(storeDTO);
        return ResponseEntity.created(new URI("/api/stores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("store", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /stores : Updates an existing store.
     *
     * @param storeDTO the storeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated storeDTO,
     * or with status 400 (Bad Request) if the storeDTO is not valid,
     * or with status 500 (Internal Server Error) if the storeDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/stores")
    @Timed
    public ResponseEntity<StoreDTO> updateStore(@Valid @RequestBody StoreDTO storeDTO) throws URISyntaxException {
        log.debug("REST request to update Store : {}", storeDTO);
        if (storeDTO.getId() == null) {
            return createStore(storeDTO);
        }
        StoreDTO result = storeService.save(storeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("store", storeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /stores : get all the stores.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of stores in body
     */
    @GetMapping("/stores")
    @Timed
    public List<StoreDTO> getAllStores() {
        log.debug("REST request to get all Stores");
        return storeService.findAll();
    }

    /**
     * GET  /stores/:id : get the "id" store.
     *
     * @param id the id of the storeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the storeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/stores/{id}")
    @Timed
    public ResponseEntity<StoreDTO> getStore(@PathVariable Long id) {
        log.debug("REST request to get Store : {}", id);
        StoreDTO storeDTO = storeService.findOne(id);
        return Optional.ofNullable(storeDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /stores/:id : delete the "id" store.
     *
     * @param id the id of the storeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/stores/{id}")
    @Timed
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        log.debug("REST request to delete Store : {}", id);
        storeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("store", id.toString())).build();
    }

}
