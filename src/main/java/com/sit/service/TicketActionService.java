package com.sit.service;

import com.sit.domain.TicketAction;
import com.sit.repository.tenant.TicketActionRepository;
import com.sit.service.dto.TicketActionDTO;
import com.sit.service.mapper.TicketActionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TicketAction.
 */
@Service
@Transactional
public class TicketActionService {

    private final Logger log = LoggerFactory.getLogger(TicketActionService.class);

    @Inject
    private TicketActionRepository ticketActionRepository;

    @Inject
    private TicketActionMapper ticketActionMapper;

    /**
     * Save a ticketAction.
     *
     * @param ticketActionDTO the entity to save
     * @return the persisted entity
     */
    public TicketActionDTO save(TicketActionDTO ticketActionDTO) {
        log.debug("Request to save TicketAction : {}", ticketActionDTO);
        TicketAction ticketAction = ticketActionMapper.ticketActionDTOToTicketAction(ticketActionDTO);
        ticketAction = ticketActionRepository.save(ticketAction);
        TicketActionDTO result = ticketActionMapper.ticketActionToTicketActionDTO(ticketAction);
        return result;
    }

    /**
     *  Get all the ticketActions.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TicketActionDTO> findAll() {
        log.debug("Request to get all TicketActions");
        List<TicketActionDTO> result = ticketActionRepository.findAll().stream()
            .map(ticketActionMapper::ticketActionToTicketActionDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one ticketAction by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TicketActionDTO findOne(Long id) {
        log.debug("Request to get TicketAction : {}", id);
        TicketAction ticketAction = ticketActionRepository.findOne(id);
        TicketActionDTO ticketActionDTO = ticketActionMapper.ticketActionToTicketActionDTO(ticketAction);
        return ticketActionDTO;
    }

    /**
     *  Delete the  ticketAction by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TicketAction : {}", id);
        ticketActionRepository.delete(id);
    }
}
