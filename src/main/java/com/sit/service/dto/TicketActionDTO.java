package com.sit.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.ActionType;

/**
 * A DTO for the TicketAction entity.
 */
public class TicketActionDTO implements Serializable {

    private Long id;

    private ActionType actionType;

    private ZonedDateTime actionDate;

    private String actionBy;


    private Long ticketId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }
    public ZonedDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(ZonedDateTime actionDate) {
        this.actionDate = actionDate;
    }
    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketActionDTO ticketActionDTO = (TicketActionDTO) o;

        if ( ! Objects.equals(id, ticketActionDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TicketActionDTO{" +
            "id=" + id +
            ", actionType='" + actionType + "'" +
            ", actionDate='" + actionDate + "'" +
            ", actionBy='" + actionBy + "'" +
            '}';
    }
}
