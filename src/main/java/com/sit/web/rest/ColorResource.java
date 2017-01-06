package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.ColorService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.ColorDTO;

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
 * REST controller for managing Color.
 */
@RestController
@RequestMapping("/api")
public class ColorResource {

    private final Logger log = LoggerFactory.getLogger(ColorResource.class);
        
    @Inject
    private ColorService colorService;

    /**
     * POST  /colors : Create a new color.
     *
     * @param colorDTO the colorDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new colorDTO, or with status 400 (Bad Request) if the color has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/colors")
    @Timed
    public ResponseEntity<ColorDTO> createColor(@Valid @RequestBody ColorDTO colorDTO) throws URISyntaxException {
        log.debug("REST request to save Color : {}", colorDTO);
        if (colorDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("color", "idexists", "A new color cannot already have an ID")).body(null);
        }
        ColorDTO result = colorService.save(colorDTO);
        return ResponseEntity.created(new URI("/api/colors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("color", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /colors : Updates an existing color.
     *
     * @param colorDTO the colorDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated colorDTO,
     * or with status 400 (Bad Request) if the colorDTO is not valid,
     * or with status 500 (Internal Server Error) if the colorDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/colors")
    @Timed
    public ResponseEntity<ColorDTO> updateColor(@Valid @RequestBody ColorDTO colorDTO) throws URISyntaxException {
        log.debug("REST request to update Color : {}", colorDTO);
        if (colorDTO.getId() == null) {
            return createColor(colorDTO);
        }
        ColorDTO result = colorService.save(colorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("color", colorDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /colors : get all the colors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of colors in body
     */
    @GetMapping("/colors")
    @Timed
    public List<ColorDTO> getAllColors() {
        log.debug("REST request to get all Colors");
        return colorService.findAll();
    }

    /**
     * GET  /colors/:id : get the "id" color.
     *
     * @param id the id of the colorDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the colorDTO, or with status 404 (Not Found)
     */
    @GetMapping("/colors/{id}")
    @Timed
    public ResponseEntity<ColorDTO> getColor(@PathVariable Long id) {
        log.debug("REST request to get Color : {}", id);
        ColorDTO colorDTO = colorService.findOne(id);
        return Optional.ofNullable(colorDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /colors/:id : delete the "id" color.
     *
     * @param id the id of the colorDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/colors/{id}")
    @Timed
    public ResponseEntity<Void> deleteColor(@PathVariable Long id) {
        log.debug("REST request to delete Color : {}", id);
        colorService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("color", id.toString())).build();
    }

}
