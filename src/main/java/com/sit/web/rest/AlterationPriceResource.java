package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.AlterationPriceService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.AlterationPriceDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing AlterationPrice.
 */
@RestController
@RequestMapping("/api")
public class AlterationPriceResource {

    private final Logger log = LoggerFactory.getLogger(AlterationPriceResource.class);
        
    @Inject
    private AlterationPriceService alterationPriceService;

    /**
     * POST  /alteration-prices : Create a new alterationPrice.
     *
     * @param alterationPriceDTO the alterationPriceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alterationPriceDTO, or with status 400 (Bad Request) if the alterationPrice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alteration-prices")
    @Timed
    public ResponseEntity<AlterationPriceDTO> createAlterationPrice(@RequestBody AlterationPriceDTO alterationPriceDTO) throws URISyntaxException {
        log.debug("REST request to save AlterationPrice : {}", alterationPriceDTO);
        if (alterationPriceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("alterationPrice", "idexists", "A new alterationPrice cannot already have an ID")).body(null);
        }
        AlterationPriceDTO result = alterationPriceService.save(alterationPriceDTO);
        return ResponseEntity.created(new URI("/api/alteration-prices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("alterationPrice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alteration-prices : Updates an existing alterationPrice.
     *
     * @param alterationPriceDTO the alterationPriceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alterationPriceDTO,
     * or with status 400 (Bad Request) if the alterationPriceDTO is not valid,
     * or with status 500 (Internal Server Error) if the alterationPriceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alteration-prices")
    @Timed
    public ResponseEntity<AlterationPriceDTO> updateAlterationPrice(@RequestBody AlterationPriceDTO alterationPriceDTO) throws URISyntaxException {
        log.debug("REST request to update AlterationPrice : {}", alterationPriceDTO);
        if (alterationPriceDTO.getId() == null) {
            return createAlterationPrice(alterationPriceDTO);
        }
        AlterationPriceDTO result = alterationPriceService.save(alterationPriceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("alterationPrice", alterationPriceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alteration-prices : get all the alterationPrices.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alterationPrices in body
     */
    @GetMapping("/alteration-prices")
    @Timed
    public List<AlterationPriceDTO> getAllAlterationPrices() {
        log.debug("REST request to get all AlterationPrices");
        return alterationPriceService.findAll();
    }

    /**
     * GET  /alteration-prices/:id : get the "id" alterationPrice.
     *
     * @param id the id of the alterationPriceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alterationPriceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/alteration-prices/{id}")
    @Timed
    public ResponseEntity<AlterationPriceDTO> getAlterationPrice(@PathVariable Long id) {
        log.debug("REST request to get AlterationPrice : {}", id);
        AlterationPriceDTO alterationPriceDTO = alterationPriceService.findOne(id);
        return Optional.ofNullable(alterationPriceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /alteration-prices/:id : delete the "id" alterationPrice.
     *
     * @param id the id of the alterationPriceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alteration-prices/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlterationPrice(@PathVariable Long id) {
        log.debug("REST request to delete AlterationPrice : {}", id);
        alterationPriceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("alterationPrice", id.toString())).build();
    }

}
