package com.sit.service;

import com.sit.domain.TicketAlteration;
import com.sit.repository.tenant.TicketAlterationRepository;
import com.sit.service.dto.TicketAlterationDTO;
import com.sit.service.mapper.TicketAlterationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing TicketAlteration.
 */
@Service
@Transactional
public class TicketAlterationService {

    private final Logger log = LoggerFactory.getLogger(TicketAlterationService.class);

    @Inject
    private TicketAlterationRepository ticketAlterationRepository;

    @Inject
    private TicketAlterationMapper ticketAlterationMapper;

    /**
     * Save a ticketAlteration.
     *
     * @param ticketAlterationDTO the entity to save
     * @return the persisted entity
     */
    public TicketAlterationDTO save(TicketAlterationDTO ticketAlterationDTO) {
        log.debug("Request to save TicketAlteration : {}", ticketAlterationDTO);
        TicketAlteration ticketAlteration = ticketAlterationMapper.ticketAlterationDTOToTicketAlteration(ticketAlterationDTO);
        ticketAlteration = ticketAlterationRepository.save(ticketAlteration);
        TicketAlterationDTO result = ticketAlterationMapper.ticketAlterationToTicketAlterationDTO(ticketAlteration);
        return result;
    }

    /**
     *  Get all the ticketAlterations.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TicketAlterationDTO> findAll() {
        log.debug("Request to get all TicketAlterations");
        List<TicketAlterationDTO> result = ticketAlterationRepository.findAll().stream()
            .map(ticketAlterationMapper::ticketAlterationToTicketAlterationDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one ticketAlteration by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TicketAlterationDTO findOne(Long id) {
        log.debug("Request to get TicketAlteration : {}", id);
        TicketAlteration ticketAlteration = ticketAlterationRepository.findOne(id);
        TicketAlterationDTO ticketAlterationDTO = ticketAlterationMapper.ticketAlterationToTicketAlterationDTO(ticketAlteration);
        return ticketAlterationDTO;
    }

    /**
     *  Delete the  ticketAlteration by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TicketAlteration : {}", id);
        ticketAlterationRepository.delete(id);
    }
}
