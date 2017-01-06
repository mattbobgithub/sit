package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.PriceCategoryService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.PriceCategoryDTO;

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
 * REST controller for managing PriceCategory.
 */
@RestController
@RequestMapping("/api")
public class PriceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(PriceCategoryResource.class);
        
    @Inject
    private PriceCategoryService priceCategoryService;

    /**
     * POST  /price-categories : Create a new priceCategory.
     *
     * @param priceCategoryDTO the priceCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new priceCategoryDTO, or with status 400 (Bad Request) if the priceCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/price-categories")
    @Timed
    public ResponseEntity<PriceCategoryDTO> createPriceCategory(@Valid @RequestBody PriceCategoryDTO priceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save PriceCategory : {}", priceCategoryDTO);
        if (priceCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("priceCategory", "idexists", "A new priceCategory cannot already have an ID")).body(null);
        }
        PriceCategoryDTO result = priceCategoryService.save(priceCategoryDTO);
        return ResponseEntity.created(new URI("/api/price-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("priceCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /price-categories : Updates an existing priceCategory.
     *
     * @param priceCategoryDTO the priceCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated priceCategoryDTO,
     * or with status 400 (Bad Request) if the priceCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the priceCategoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/price-categories")
    @Timed
    public ResponseEntity<PriceCategoryDTO> updatePriceCategory(@Valid @RequestBody PriceCategoryDTO priceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update PriceCategory : {}", priceCategoryDTO);
        if (priceCategoryDTO.getId() == null) {
            return createPriceCategory(priceCategoryDTO);
        }
        PriceCategoryDTO result = priceCategoryService.save(priceCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("priceCategory", priceCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /price-categories : get all the priceCategories.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of priceCategories in body
     */
    @GetMapping("/price-categories")
    @Timed
    public List<PriceCategoryDTO> getAllPriceCategories() {
        log.debug("REST request to get all PriceCategories");
        return priceCategoryService.findAll();
    }

    /**
     * GET  /price-categories/:id : get the "id" priceCategory.
     *
     * @param id the id of the priceCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the priceCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/price-categories/{id}")
    @Timed
    public ResponseEntity<PriceCategoryDTO> getPriceCategory(@PathVariable Long id) {
        log.debug("REST request to get PriceCategory : {}", id);
        PriceCategoryDTO priceCategoryDTO = priceCategoryService.findOne(id);
        return Optional.ofNullable(priceCategoryDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /price-categories/:id : delete the "id" priceCategory.
     *
     * @param id the id of the priceCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/price-categories/{id}")
    @Timed
    public ResponseEntity<Void> deletePriceCategory(@PathVariable Long id) {
        log.debug("REST request to delete PriceCategory : {}", id);
        priceCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("priceCategory", id.toString())).build();
    }

}
