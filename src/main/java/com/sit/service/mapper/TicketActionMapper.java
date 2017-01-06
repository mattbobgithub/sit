package com.sit.service.mapper;

import com.sit.domain.Ticket;
import com.sit.domain.TicketAction;
import com.sit.service.dto.TicketActionDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity TicketAction and its DTO TicketActionDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketActionMapper {

    @Mapping(source = "ticket.id", target = "ticketId")
    TicketActionDTO ticketActionToTicketActionDTO(TicketAction ticketAction);

    List<TicketActionDTO> ticketActionsToTicketActionDTOs(List<TicketAction> ticketActions);

    @Mapping(source = "ticketId", target = "ticket")
    TicketAction ticketActionDTOToTicketAction(TicketActionDTO ticketActionDTO);

    List<TicketAction> ticketActionDTOsToTicketActions(List<TicketActionDTO> ticketActionDTOs);

    default Ticket ticketFromId(Long id) {
        if (id == null) {
            return null;
        }
        Ticket ticket = new Ticket();
        ticket.setId(id);
        return ticket;
    }
}
