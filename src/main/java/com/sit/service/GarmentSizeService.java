package com.sit.service;

import com.sit.domain.GarmentSize;
import com.sit.repository.tenant.GarmentSizeRepository;
import com.sit.service.dto.GarmentSizeDTO;
import com.sit.service.mapper.GarmentSizeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing GarmentSize.
 */
@Service
@Transactional
public class GarmentSizeService {

    private final Logger log = LoggerFactory.getLogger(GarmentSizeService.class);

    @Inject
    private GarmentSizeRepository garmentSizeRepository;

    @Inject
    private GarmentSizeMapper garmentSizeMapper;

    /**
     * Save a garmentSize.
     *
     * @param garmentSizeDTO the entity to save
     * @return the persisted entity
     */
    public GarmentSizeDTO save(GarmentSizeDTO garmentSizeDTO) {
        log.debug("Request to save GarmentSize : {}", garmentSizeDTO);
        GarmentSize garmentSize = garmentSizeMapper.garmentSizeDTOToGarmentSize(garmentSizeDTO);
        garmentSize = garmentSizeRepository.save(garmentSize);
        GarmentSizeDTO result = garmentSizeMapper.garmentSizeToGarmentSizeDTO(garmentSize);
        return result;
    }

    /**
     *  Get all the garmentSizes.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GarmentSizeDTO> findAll() {
        log.debug("Request to get all GarmentSizes");
        List<GarmentSizeDTO> result = garmentSizeRepository.findAll().stream()
            .map(garmentSizeMapper::garmentSizeToGarmentSizeDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one garmentSize by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GarmentSizeDTO findOne(Long id) {
        log.debug("Request to get GarmentSize : {}", id);
        GarmentSize garmentSize = garmentSizeRepository.findOne(id);
        GarmentSizeDTO garmentSizeDTO = garmentSizeMapper.garmentSizeToGarmentSizeDTO(garmentSize);
        return garmentSizeDTO;
    }

    /**
     *  Delete the  garmentSize by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GarmentSize : {}", id);
        garmentSizeRepository.delete(id);
    }
}
