package com.sit.service;

import com.sit.domain.Store;
import com.sit.repository.tenant.StoreRepository;
import com.sit.service.dto.StoreDTO;
import com.sit.service.mapper.StoreMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Store.
 */
@Service
@Transactional
public class StoreService {

    private final Logger log = LoggerFactory.getLogger(StoreService.class);

    @Inject
    private StoreRepository storeRepository;

    @Inject
    private StoreMapper storeMapper;

    /**
     * Save a store.
     *
     * @param storeDTO the entity to save
     * @return the persisted entity
     */
    public StoreDTO save(StoreDTO storeDTO) {
        log.debug("Request to save Store : {}", storeDTO);
        Store store = storeMapper.storeDTOToStore(storeDTO);
        store = storeRepository.save(store);
        StoreDTO result = storeMapper.storeToStoreDTO(store);
        return result;
    }

    /**
     *  Get all the stores.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<StoreDTO> findAll() {
        log.debug("Request to get all Stores");
        List<StoreDTO> result = storeRepository.findAll().stream()
            .map(storeMapper::storeToStoreDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one store by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public StoreDTO findOne(Long id) {
        log.debug("Request to get Store : {}", id);
        Store store = storeRepository.findOne(id);
        StoreDTO storeDTO = storeMapper.storeToStoreDTO(store);
        return storeDTO;
    }

    /**
     *  Delete the  store by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Store : {}", id);
        storeRepository.delete(id);
    }
}
