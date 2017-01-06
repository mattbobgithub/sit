package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.service.WorkroomMetricService;
import com.sit.web.rest.util.HeaderUtil;
import com.sit.service.dto.WorkroomMetricDTO;

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
 * REST controller for managing WorkroomMetric.
 */
@RestController
@RequestMapping("/api")
public class WorkroomMetricResource {

    private final Logger log = LoggerFactory.getLogger(WorkroomMetricResource.class);
        
    @Inject
    private WorkroomMetricService workroomMetricService;

    /**
     * POST  /workroom-metrics : Create a new workroomMetric.
     *
     * @param workroomMetricDTO the workroomMetricDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workroomMetricDTO, or with status 400 (Bad Request) if the workroomMetric has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/workroom-metrics")
    @Timed
    public ResponseEntity<WorkroomMetricDTO> createWorkroomMetric(@RequestBody WorkroomMetricDTO workroomMetricDTO) throws URISyntaxException {
        log.debug("REST request to save WorkroomMetric : {}", workroomMetricDTO);
        if (workroomMetricDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("workroomMetric", "idexists", "A new workroomMetric cannot already have an ID")).body(null);
        }
        WorkroomMetricDTO result = workroomMetricService.save(workroomMetricDTO);
        return ResponseEntity.created(new URI("/api/workroom-metrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("workroomMetric", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /workroom-metrics : Updates an existing workroomMetric.
     *
     * @param workroomMetricDTO the workroomMetricDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workroomMetricDTO,
     * or with status 400 (Bad Request) if the workroomMetricDTO is not valid,
     * or with status 500 (Internal Server Error) if the workroomMetricDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/workroom-metrics")
    @Timed
    public ResponseEntity<WorkroomMetricDTO> updateWorkroomMetric(@RequestBody WorkroomMetricDTO workroomMetricDTO) throws URISyntaxException {
        log.debug("REST request to update WorkroomMetric : {}", workroomMetricDTO);
        if (workroomMetricDTO.getId() == null) {
            return createWorkroomMetric(workroomMetricDTO);
        }
        WorkroomMetricDTO result = workroomMetricService.save(workroomMetricDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("workroomMetric", workroomMetricDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /workroom-metrics : get all the workroomMetrics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of workroomMetrics in body
     */
    @GetMapping("/workroom-metrics")
    @Timed
    public List<WorkroomMetricDTO> getAllWorkroomMetrics() {
        log.debug("REST request to get all WorkroomMetrics");
        return workroomMetricService.findAll();
    }

    /**
     * GET  /workroom-metrics/:id : get the "id" workroomMetric.
     *
     * @param id the id of the workroomMetricDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workroomMetricDTO, or with status 404 (Not Found)
     */
    @GetMapping("/workroom-metrics/{id}")
    @Timed
    public ResponseEntity<WorkroomMetricDTO> getWorkroomMetric(@PathVariable Long id) {
        log.debug("REST request to get WorkroomMetric : {}", id);
        WorkroomMetricDTO workroomMetricDTO = workroomMetricService.findOne(id);
        return Optional.ofNullable(workroomMetricDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /workroom-metrics/:id : delete the "id" workroomMetric.
     *
     * @param id the id of the workroomMetricDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/workroom-metrics/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkroomMetric(@PathVariable Long id) {
        log.debug("REST request to delete WorkroomMetric : {}", id);
        workroomMetricService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("workroomMetric", id.toString())).build();
    }

}
