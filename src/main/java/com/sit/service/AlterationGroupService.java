package com.sit.service;

import com.sit.domain.AlterationGroup;
import com.sit.repository.tenant.AlterationGroupRepository;
import com.sit.service.dto.AlterationGroupDTO;
import com.sit.service.mapper.AlterationGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AlterationGroup.
 */
@Service
@Transactional
public class AlterationGroupService {

    private final Logger log = LoggerFactory.getLogger(AlterationGroupService.class);

    @Inject
    private AlterationGroupRepository alterationGroupRepository;

    @Inject
    private AlterationGroupMapper alterationGroupMapper;

    /**
     * Save a alterationGroup.
     *
     * @param alterationGroupDTO the entity to save
     * @return the persisted entity
     */
    public AlterationGroupDTO save(AlterationGroupDTO alterationGroupDTO) {
        log.debug("Request to save AlterationGroup : {}", alterationGroupDTO);
        AlterationGroup alterationGroup = alterationGroupMapper.alterationGroupDTOToAlterationGroup(alterationGroupDTO);
        alterationGroup = alterationGroupRepository.save(alterationGroup);
        AlterationGroupDTO result = alterationGroupMapper.alterationGroupToAlterationGroupDTO(alterationGroup);
        return result;
    }

    /**
     *  Get all the alterationGroups.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlterationGroupDTO> findAll() {
        log.debug("Request to get all AlterationGroups");
        List<AlterationGroupDTO> result = alterationGroupRepository.findAllWithEagerRelationships().stream()
            .map(alterationGroupMapper::alterationGroupToAlterationGroupDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one alterationGroup by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AlterationGroupDTO findOne(Long id) {
        log.debug("Request to get AlterationGroup : {}", id);
        AlterationGroup alterationGroup = alterationGroupRepository.findOneWithEagerRelationships(id);
        AlterationGroupDTO alterationGroupDTO = alterationGroupMapper.alterationGroupToAlterationGroupDTO(alterationGroup);
        return alterationGroupDTO;
    }

    /**
     *  Delete the  alterationGroup by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AlterationGroup : {}", id);
        alterationGroupRepository.delete(id);
    }
}
