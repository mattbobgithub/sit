package com.sit.service;

import com.sit.domain.AlterationPrice;
import com.sit.repository.tenant.AlterationPriceRepository;
import com.sit.service.dto.AlterationPriceDTO;
import com.sit.service.mapper.AlterationPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing AlterationPrice.
 */
@Service
@Transactional
public class AlterationPriceService {

    private final Logger log = LoggerFactory.getLogger(AlterationPriceService.class);

    @Inject
    private AlterationPriceRepository alterationPriceRepository;

    @Inject
    private AlterationPriceMapper alterationPriceMapper;

    /**
     * Save a alterationPrice.
     *
     * @param alterationPriceDTO the entity to save
     * @return the persisted entity
     */
    public AlterationPriceDTO save(AlterationPriceDTO alterationPriceDTO) {
        log.debug("Request to save AlterationPrice : {}", alterationPriceDTO);
        AlterationPrice alterationPrice = alterationPriceMapper.alterationPriceDTOToAlterationPrice(alterationPriceDTO);
        alterationPrice = alterationPriceRepository.save(alterationPrice);
        AlterationPriceDTO result = alterationPriceMapper.alterationPriceToAlterationPriceDTO(alterationPrice);
        return result;
    }

    /**
     *  Get all the alterationPrices.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<AlterationPriceDTO> findAll() {
        log.debug("Request to get all AlterationPrices");
        List<AlterationPriceDTO> result = alterationPriceRepository.findAll().stream()
            .map(alterationPriceMapper::alterationPriceToAlterationPriceDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one alterationPrice by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public AlterationPriceDTO findOne(Long id) {
        log.debug("Request to get AlterationPrice : {}", id);
        AlterationPrice alterationPrice = alterationPriceRepository.findOne(id);
        AlterationPriceDTO alterationPriceDTO = alterationPriceMapper.alterationPriceToAlterationPriceDTO(alterationPrice);
        return alterationPriceDTO;
    }

    /**
     *  Delete the  alterationPrice by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete AlterationPrice : {}", id);
        alterationPriceRepository.delete(id);
    }
}
