package com.sit.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.sit.domain.SitUser;
import com.sit.security.SecurityUtils;
import com.sit.service.SitUserService;
import com.sit.service.dto.SitUserDTO;
import com.sit.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SitUser.
 */
@RestController
@RequestMapping("/api")
public class SitUserResource {

    private final Logger log = LoggerFactory.getLogger(SitUserResource.class);

    @Inject
    private SitUserService sitUserService;

    /**
     * POST  /sit-users : Create a new sitUser.
     *
     * @param sitUserDTO the sitUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new sitUserDTO, or with status 400 (Bad Request) if the sitUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/sit-users")
    @Timed
    public ResponseEntity<SitUserDTO> createSitUser(@RequestBody SitUserDTO sitUserDTO) throws URISyntaxException {
        log.debug("REST request to save SitUser : {}", sitUserDTO);
        if (sitUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("sitUser", "idexists", "A new sitUser cannot already have an ID")).body(null);
        }
        SitUserDTO result = sitUserService.save(sitUserDTO);
        return ResponseEntity.created(new URI("/api/sit-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sitUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sit-users : Updates an existing sitUser.
     *
     * @param sitUserDTO the sitUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated sitUserDTO,
     * or with status 400 (Bad Request) if the sitUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the sitUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/sit-users")
    @Timed
    public ResponseEntity<SitUserDTO> updateSitUser(@RequestBody SitUserDTO sitUserDTO) throws URISyntaxException {
        log.debug("REST request to update SitUser : {}", sitUserDTO);


        //MTC - update user roles from usertype


        if (sitUserDTO.getId() == null) {
            return createSitUser(sitUserDTO);
        }
        SitUserDTO result = sitUserService.save(sitUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sitUser", sitUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sit-users : get all the sitUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of sitUsers in body
     */
    // MTC - override to return only users that current logged in user can access,
    @GetMapping("/sit-users")
    @Timed
    public List<SitUserDTO> getAllSitUsers() {
        log.debug("REST request to get all SitUsers");
        List<SitUserDTO> result = new ArrayList<>();
       if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
           result = sitUserService.findAll();
       }
           else {
           if (SecurityUtils.isCurrentUserInRole("ROLE_MANAGER")) {
               SitUser sitUser = sitUserService.getByUsername(SecurityUtils.getCurrentUserLogin());
               result = sitUserService.findAllForSitUser(sitUser);

           }
       }
          return result;
    }

    /**
     * GET  /sit-users/:id : get the "id" sitUser.
     *
     * @param id the id of the sitUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the sitUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/sit-users/{id}")
    @Timed
    public ResponseEntity<SitUserDTO> getSitUser(@PathVariable Long id) {
        log.debug("REST request to get SitUser : {}", id);
        SitUserDTO sitUserDTO = sitUserService.findOne(id);
        return Optional.ofNullable(sitUserDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sit-users/:id : delete the "id" sitUser.
     *
     * @param id the id of the sitUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/sit-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteSitUser(@PathVariable Long id) {
        log.debug("REST request to delete SitUser : {}", id);
        sitUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sitUser", id.toString())).build();
    }

}
