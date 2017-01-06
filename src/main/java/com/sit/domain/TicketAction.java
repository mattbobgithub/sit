package com.sit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.sit.domain.enumeration.ActionType;

/**
 * A TicketAction.
 */
@Entity
@Table(name = "ticket_action")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TicketAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type")
    private ActionType actionType;

    @Column(name = "action_date")
    private ZonedDateTime actionDate;

    @Column(name = "action_by")
    private String actionBy;

    @ManyToOne
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public TicketAction actionType(ActionType actionType) {
        this.actionType = actionType;
        return this;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public ZonedDateTime getActionDate() {
        return actionDate;
    }

    public TicketAction actionDate(ZonedDateTime actionDate) {
        this.actionDate = actionDate;
        return this;
    }

    public void setActionDate(ZonedDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public String getActionBy() {
        return actionBy;
    }

    public TicketAction actionBy(String actionBy) {
        this.actionBy = actionBy;
        return this;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public TicketAction ticket(Ticket ticket) {
        this.ticket = ticket;
        return this;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketAction ticketAction = (TicketAction) o;
        if (ticketAction.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ticketAction.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TicketAction{" +
            "id=" + id +
            ", actionType='" + actionType + "'" +
            ", actionDate='" + actionDate + "'" +
            ", actionBy='" + actionBy + "'" +
            '}';
    }
}
