package com.sit.service;

import com.sit.domain.Garment;
import com.sit.repository.tenant.GarmentRepository;
import com.sit.service.dto.GarmentDTO;
import com.sit.service.mapper.GarmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Garment.
 */
@Service
@Transactional
public class GarmentService {

    private final Logger log = LoggerFactory.getLogger(GarmentService.class);

    @Inject
    private GarmentRepository garmentRepository;

    @Inject
    private GarmentMapper garmentMapper;

    /**
     * Save a garment.
     *
     * @param garmentDTO the entity to save
     * @return the persisted entity
     */
    public GarmentDTO save(GarmentDTO garmentDTO) {
        log.debug("Request to save Garment : {}", garmentDTO);
        Garment garment = garmentMapper.garmentDTOToGarment(garmentDTO);
        garment = garmentRepository.save(garment);
        GarmentDTO result = garmentMapper.garmentToGarmentDTO(garment);
        return result;
    }

    /**
     *  Get all the garments.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GarmentDTO> findAll() {
        log.debug("Request to get all Garments");
        List<GarmentDTO> result = garmentRepository.findAll().stream()
            .map(garmentMapper::garmentToGarmentDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one garment by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public GarmentDTO findOne(Long id) {
        log.debug("Request to get Garment : {}", id);
        Garment garment = garmentRepository.findOne(id);
        GarmentDTO garmentDTO = garmentMapper.garmentToGarmentDTO(garment);
        return garmentDTO;
    }

    /**
     *  Delete the  garment by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Garment : {}", id);
        garmentRepository.delete(id);
    }
}
