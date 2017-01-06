package com.sit.service;

import com.sit.domain.Color;
import com.sit.repository.tenant.ColorRepository;
import com.sit.service.dto.ColorDTO;
import com.sit.service.mapper.ColorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Color.
 */
@Service
@Transactional
public class ColorService {

    private final Logger log = LoggerFactory.getLogger(ColorService.class);

    @Inject
    private ColorRepository colorRepository;

    @Inject
    private ColorMapper colorMapper;

    /**
     * Save a color.
     *
     * @param colorDTO the entity to save
     * @return the persisted entity
     */
    public ColorDTO save(ColorDTO colorDTO) {
        log.debug("Request to save Color : {}", colorDTO);
        Color color = colorMapper.colorDTOToColor(colorDTO);
        color = colorRepository.save(color);
        ColorDTO result = colorMapper.colorToColorDTO(color);
        return result;
    }

    /**
     *  Get all the colors.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ColorDTO> findAll() {
        log.debug("Request to get all Colors");
        List<ColorDTO> result = colorRepository.findAll().stream()
            .map(colorMapper::colorToColorDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one color by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ColorDTO findOne(Long id) {
        log.debug("Request to get Color : {}", id);
        Color color = colorRepository.findOne(id);
        ColorDTO colorDTO = colorMapper.colorToColorDTO(color);
        return colorDTO;
    }

    /**
     *  Delete the  color by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Color : {}", id);
        colorRepository.delete(id);
    }
}
