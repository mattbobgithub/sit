package com.sit.service;

import com.sit.domain.AlterationSubGroup;
import com.sit.repository.tenant.AlterationSubGroupRepository;
import com.sit.service.dto.AlterationSubGroupDTO;
import com.sit.service.mapper.AlterationSubGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AlterationSubGroup.
 */
@Service
@Transactional
public class AlterationSubGroupService {

    private final Logger log = LoggerFactory.getLogger(AlterationSubGroupService.class);

    @Inject
    private AlterationSubGroupRepository alterationSubGroupRepository;

    @Inject
    private AlterationSubGroupMapper alterationSubGroupMapper;

    /**
     * Save a alterationSubGroup.
     *
     * @param alterationSubGroupDTO the entity to save
     * @return the persisted entity
     */
    public AlterationSubGroupDTO save(AlterationSubGroupDTO alterationSubGroupDTO) {
        log.debug("Request to save AlterationSubGroup : {}", alterationSubGroupDTO);
        AlterationSubGroup alterationSubGroup = alterationSubGroupMapper.alterationSubGroupDTOToAlterationSubGroup(alterationSubGroupDTO);
        alterationSubGroup = alterationSubGroupRepository.save(alterationSubGroup);
        AlterationSubGroupDTO result = alterationSubGroupMapper.alterationSubGroupToAlterationSubGroupDTO(alterationSubGroup);
        return result;
    }

    /**
     *  Get all the alterationSubGroups.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlterationSubGroupDTO> findAll() {
        log.debug("Request to get all AlterationSubGroups");
        List<AlterationSubGroupDTO> result = alterationSubGroupRepository.findAll().stream()
            .map(alterationSubGroupMapper::alterationSubGroupToAlterationSubGroupDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one alterationSubGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AlterationSubGroupDTO findOne(Long id) {
        log.debug("Request to get AlterationSubGroup : {}", id);
        AlterationSubGroup alterationSubGroup = alterationSubGroupRepository.findOne(id);
        AlterationSubGroupDTO alterationSubGroupDTO = alterationSubGroupMapper.alterationSubGroupToAlterationSubGroupDTO(alterationSubGroup);
        return alterationSubGroupDTO;
    }

    /**
     *  Delete the  alterationSubGroup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AlterationSubGroup : {}", id);
        alterationSubGroupRepository.delete(id);
    }
}
