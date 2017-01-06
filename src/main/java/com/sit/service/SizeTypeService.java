package com.sit.service;

import com.sit.domain.SizeType;
import com.sit.repository.tenant.SizeTypeRepository;
import com.sit.service.dto.SizeTypeDTO;
import com.sit.service.mapper.SizeTypeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing SizeType.
 */
@Service
@Transactional
public class SizeTypeService {

    private final Logger log = LoggerFactory.getLogger(SizeTypeService.class);

    @Inject
    private SizeTypeRepository sizeTypeRepository;

    @Inject
    private SizeTypeMapper sizeTypeMapper;

    /**
     * Save a sizeType.
     *
     * @param sizeTypeDTO the entity to save
     * @return the persisted entity
     */
    public SizeTypeDTO save(SizeTypeDTO sizeTypeDTO) {
        log.debug("Request to save SizeType : {}", sizeTypeDTO);
        SizeType sizeType = sizeTypeMapper.sizeTypeDTOToSizeType(sizeTypeDTO);
        sizeType = sizeTypeRepository.save(sizeType);
        SizeTypeDTO result = sizeTypeMapper.sizeTypeToSizeTypeDTO(sizeType);
        return result;
    }

    /**
     *  Get all the sizeTypes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<SizeTypeDTO> findAll() {
        log.debug("Request to get all SizeTypes");
        List<SizeTypeDTO> result = sizeTypeRepository.findAll().stream()
            .map(sizeTypeMapper::sizeTypeToSizeTypeDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one sizeType by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public SizeTypeDTO findOne(Long id) {
        log.debug("Request to get SizeType : {}", id);
        SizeType sizeType = sizeTypeRepository.findOne(id);
        SizeTypeDTO sizeTypeDTO = sizeTypeMapper.sizeTypeToSizeTypeDTO(sizeType);
        return sizeTypeDTO;
    }

    /**
     *  Delete the  sizeType by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete SizeType : {}", id);
        sizeTypeRepository.delete(id);
    }
}
