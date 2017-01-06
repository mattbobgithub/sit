package com.sit.service;

import com.sit.domain.Alteration;
import com.sit.repository.tenant.AlterationRepository;
import com.sit.service.dto.AlterationDTO;
import com.sit.service.mapper.AlterationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Alteration.
 */
@Service
@Transactional
public class AlterationService {

    private final Logger log = LoggerFactory.getLogger(AlterationService.class);

    @Inject
    private AlterationRepository alterationRepository;

    @Inject
    private AlterationMapper alterationMapper;

    /**
     * Save a alteration.
     *
     * @param alterationDTO the entity to save
     * @return the persisted entity
     */
    public AlterationDTO save(AlterationDTO alterationDTO) {
        log.debug("Request to save Alteration : {}", alterationDTO);
        Alteration alteration = alterationMapper.alterationDTOToAlteration(alterationDTO);
        alteration = alterationRepository.save(alteration);
        AlterationDTO result = alterationMapper.alterationToAlterationDTO(alteration);
        return result;
    }

    /**
     *  Get all the alterations.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlterationDTO> findAll() {
        log.debug("Request to get all Alterations");
        List<AlterationDTO> result = alterationRepository.findAllWithEagerRelationships().stream()
            .map(alterationMapper::alterationToAlterationDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one alteration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AlterationDTO findOne(Long id) {
        log.debug("Request to get Alteration : {}", id);
        Alteration alteration = alterationRepository.findOneWithEagerRelationships(id);
        AlterationDTO alterationDTO = alterationMapper.alterationToAlterationDTO(alteration);
        return alterationDTO;
    }

    /**
     *  Delete the  alteration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Alteration : {}", id);
        alterationRepository.delete(id);
    }
}
