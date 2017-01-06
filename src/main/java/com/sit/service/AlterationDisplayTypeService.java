package com.sit.service;

import com.sit.domain.AlterationDisplayType;
import com.sit.repository.tenant.AlterationDisplayTypeRepository;
import com.sit.service.dto.AlterationDisplayTypeDTO;
import com.sit.service.mapper.AlterationDisplayTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AlterationDisplayType.
 */
@Service
@Transactional
public class AlterationDisplayTypeService {

    private final Logger log = LoggerFactory.getLogger(AlterationDisplayTypeService.class);

    @Inject
    private AlterationDisplayTypeRepository alterationDisplayTypeRepository;

    @Inject
    private AlterationDisplayTypeMapper alterationDisplayTypeMapper;

    /**
     * Save a alterationDisplayType.
     *
     * @param alterationDisplayTypeDTO the entity to save
     * @return the persisted entity
     */
    public AlterationDisplayTypeDTO save(AlterationDisplayTypeDTO alterationDisplayTypeDTO) {
        log.debug("Request to save AlterationDisplayType : {}", alterationDisplayTypeDTO);
        AlterationDisplayType alterationDisplayType = alterationDisplayTypeMapper.alterationDisplayTypeDTOToAlterationDisplayType(alterationDisplayTypeDTO);
        alterationDisplayType = alterationDisplayTypeRepository.save(alterationDisplayType);
        AlterationDisplayTypeDTO result = alterationDisplayTypeMapper.alterationDisplayTypeToAlterationDisplayTypeDTO(alterationDisplayType);
        return result;
    }

    /**
     *  Get all the alterationDisplayTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlterationDisplayTypeDTO> findAll() {
        log.debug("Request to get all AlterationDisplayTypes");
        List<AlterationDisplayTypeDTO> result = alterationDisplayTypeRepository.findAll().stream()
            .map(alterationDisplayTypeMapper::alterationDisplayTypeToAlterationDisplayTypeDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one alterationDisplayType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AlterationDisplayTypeDTO findOne(Long id) {
        log.debug("Request to get AlterationDisplayType : {}", id);
        AlterationDisplayType alterationDisplayType = alterationDisplayTypeRepository.findOne(id);
        AlterationDisplayTypeDTO alterationDisplayTypeDTO = alterationDisplayTypeMapper.alterationDisplayTypeToAlterationDisplayTypeDTO(alterationDisplayType);
        return alterationDisplayTypeDTO;
    }

    /**
     *  Delete the  alterationDisplayType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AlterationDisplayType : {}", id);
        alterationDisplayTypeRepository.delete(id);
    }
}
