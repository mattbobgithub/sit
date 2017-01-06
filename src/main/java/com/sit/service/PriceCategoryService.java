package com.sit.service;

import com.sit.domain.PriceCategory;
import com.sit.repository.tenant.PriceCategoryRepository;
import com.sit.service.dto.PriceCategoryDTO;
import com.sit.service.mapper.PriceCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PriceCategory.
 */
@Service
@Transactional
public class PriceCategoryService {

    private final Logger log = LoggerFactory.getLogger(PriceCategoryService.class);

    @Inject
    private PriceCategoryRepository priceCategoryRepository;

    @Inject
    private PriceCategoryMapper priceCategoryMapper;

    /**
     * Save a priceCategory.
     *
     * @param priceCategoryDTO the entity to save
     * @return the persisted entity
     */
    public PriceCategoryDTO save(PriceCategoryDTO priceCategoryDTO) {
        log.debug("Request to save PriceCategory : {}", priceCategoryDTO);
        PriceCategory priceCategory = priceCategoryMapper.priceCategoryDTOToPriceCategory(priceCategoryDTO);
        priceCategory = priceCategoryRepository.save(priceCategory);
        PriceCategoryDTO result = priceCategoryMapper.priceCategoryToPriceCategoryDTO(priceCategory);
        return result;
    }

    /**
     *  Get all the priceCategories.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<PriceCategoryDTO> findAll() {
        log.debug("Request to get all PriceCategories");
        List<PriceCategoryDTO> result = priceCategoryRepository.findAll().stream()
            .map(priceCategoryMapper::priceCategoryToPriceCategoryDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one priceCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public PriceCategoryDTO findOne(Long id) {
        log.debug("Request to get PriceCategory : {}", id);
        PriceCategory priceCategory = priceCategoryRepository.findOne(id);
        PriceCategoryDTO priceCategoryDTO = priceCategoryMapper.priceCategoryToPriceCategoryDTO(priceCategory);
        return priceCategoryDTO;
    }

    /**
     *  Delete the  priceCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete PriceCategory : {}", id);
        priceCategoryRepository.delete(id);
    }
}
