package com.sit.service.mapper;

import com.sit.domain.*;
import com.sit.service.dto.TicketDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity Ticket and its DTO TicketDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TicketMapper {

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "store.id", target = "storeId")
    @Mapping(source = "workroom.id", target = "workroomId")
    @Mapping(source = "transfer.id", target = "transferId")
    TicketDTO ticketToTicketDTO(Ticket ticket);

    List<TicketDTO> ticketsToTicketDTOs(List<Ticket> tickets);

    @Mapping(target = "ticketAlterations", ignore = true)
    @Mapping(target = "ticketActions", ignore = true)
    @Mapping(source = "customerId", target = "customer")
    @Mapping(source = "storeId", target = "store")
    @Mapping(source = "workroomId", target = "workroom")
    @Mapping(source = "transferId", target = "transfer")
    Ticket ticketDTOToTicket(TicketDTO ticketDTO);

    List<Ticket> ticketDTOsToTickets(List<TicketDTO> ticketDTOs);

    default Customer customerFromId(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    default Store storeFromId(Long id) {
        if (id == null) {
            return null;
        }
        Store store = new Store();
        store.setId(id);
        return store;
    }

    default Workroom workroomFromId(Long id) {
        if (id == null) {
            return null;
        }
        Workroom workroom = new Workroom();
        workroom.setId(id);
        return workroom;
    }

    default Transfer transferFromId(Long id) {
        if (id == null) {
            return null;
        }
        Transfer transfer = new Transfer();
        transfer.setId(id);
        return transfer;
    }
}
