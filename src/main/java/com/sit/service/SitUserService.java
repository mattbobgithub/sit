package com.sit.service;

import com.sit.domain.SitUser;
import com.sit.repository.master.SitUserRepository;
import com.sit.service.dto.SitUserDTO;
import com.sit.service.mapper.SitUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SitUser.
 */
@Service
@Transactional
public class SitUserService {

    private final Logger log = LoggerFactory.getLogger(SitUserService.class);

    @Inject
    private SitUserRepository sitUserRepository;

    @Inject
    private SitUserMapper sitUserMapper;

    /**
     * Save a sitUser.
     *
     * @param sitUserDTO the entity to save
     * @return the persisted entity
     */
    public SitUserDTO save(SitUserDTO sitUserDTO) {
        log.debug("Request to save SitUser : {}", sitUserDTO);
        SitUser sitUser = sitUserMapper.sitUserDTOToSitUser(sitUserDTO);
        sitUser = sitUserRepository.save(sitUser);
        SitUserDTO result = sitUserMapper.sitUserToSitUserDTO(sitUser);
        return result;
    }

    /**
     *  Get all the sitUsers.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SitUserDTO> findAll() {
        log.debug("Request to get all SitUsers");
        List<SitUserDTO> result = sitUserRepository.findAll().stream()
            .map(sitUserMapper::sitUserToSitUserDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one sitUser by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SitUserDTO findOne(Long id) {
        log.debug("Request to get SitUser : {}", id);
        SitUser sitUser = sitUserRepository.findOne(id);
        SitUserDTO sitUserDTO = sitUserMapper.sitUserToSitUserDTO(sitUser);
        return sitUserDTO;
    }

    /**
     *  Delete the  sitUser by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SitUser : {}", id);
        sitUserRepository.delete(id);
    }

//MTC added method to get by username

    @Transactional(readOnly = true)
    public SitUser getByUsername(String username) {
        log.debug("Request to get SitUser : {}", username);
        SitUser sitUser = sitUserRepository.findByUsername(username);
        return sitUser;
    }
}
