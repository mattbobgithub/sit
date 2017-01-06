package com.sit.service.mapper;

import com.sit.domain.Ticket;
import com.sit.domain.TicketAlteration;
import com.sit.service.dto.TicketAlterationDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TicketAlteration and its DTO TicketAlterationDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketAlterationMapper {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketAlterationDTO ticketAlterationToTicketAlterationDTO(TicketAlteration ticketAlteration);

    List<TicketAlterationDTO> ticketAlterationsToTicketAlterationDTOs(List<TicketAlteration> ticketAlterations);

    @Mapping(source = "ticketId", target = "ticket")
    TicketAlteration ticketAlterationDTOToTicketAlteration(TicketAlterationDTO ticketAlterationDTO);

    List<TicketAlteration> ticketAlterationDTOsToTicketAlterations(List<TicketAlterationDTO> ticketAlterationDTOs);

    default Ticket ticketFromId(Long id) {
        if (id == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
