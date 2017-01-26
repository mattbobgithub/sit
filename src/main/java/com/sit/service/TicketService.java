package com.sit.service;

import com.sit.domain.Ticket;
import com.sit.repository.tenant.TicketRepository;
import com.sit.service.dto.TicketDTO;
import com.sit.service.mapper.TicketMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Ticket.
 */
@Service
@Transactional
public class TicketService {

    private final Logger log = LoggerFactory.getLogger(TicketService.class);

    @Inject
    private TicketRepository ticketRepository;

    @Inject
    private TicketMapper ticketMapper;

    /**
     * Save a ticket.
     *
     * @param ticketDTO the entity to save
     * @return the persisted entity
     */
    public TicketDTO save(TicketDTO ticketDTO) {
        log.debug("Request to save Ticket : {}", ticketDTO);
        Ticket ticket = ticketMapper.ticketDTOToTicket(ticketDTO);
        ticket = ticketRepository.save(ticket);
        TicketDTO result = ticketMapper.ticketToTicketDTO(ticket);
        return result;
    }

    /**
     *  Get all the tickets.
     *
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<TicketDTO> findAll() {
        log.debug("Request to get all Tickets");
        List<TicketDTO> result = ticketRepository.findAll().stream()
            .map(ticketMapper::ticketToTicketDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one ticket by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public TicketDTO findOne(Long id) {
        log.debug("Request to get Ticket : {}", id);
        if (id==0){
            Ticket newTicket = new Ticket();
            return ticketMapper.ticketToTicketDTO(newTicket);
        }
        else{
            Ticket ticket = ticketRepository.findOne(id);
            TicketDTO ticketDTO = ticketMapper.ticketToTicketDTO(ticket);
            return ticketDTO;
        }

    }

    /**
     *  Delete the  ticket by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Ticket : {}", id);
        ticketRepository.delete(id);
    }
}
