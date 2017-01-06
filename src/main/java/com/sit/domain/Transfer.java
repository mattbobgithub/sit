package com.sit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sit.domain.enumeration.TransferStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A Transfer.
 */
@Entity
@Table(name = "transfer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Transfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "from_workroom_id")
    private Long fromWorkroomId;

    @Column(name = "to_workroom_id")
    private Long toWorkroomId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransferStatus status;

    @OneToMany(mappedBy = "transfer")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ticket> tickets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFromWorkroomId() {
        return fromWorkroomId;
    }

    public Transfer fromWorkroomId(Long fromWorkroomId) {
        this.fromWorkroomId = fromWorkroomId;
        return this;
    }

    public void setFromWorkroomId(Long fromWorkroomId) {
        this.fromWorkroomId = fromWorkroomId;
    }

    public Long getToWorkroomId() {
        return toWorkroomId;
    }

    public Transfer toWorkroomId(Long toWorkroomId) {
        this.toWorkroomId = toWorkroomId;
        return this;
    }

    public void setToWorkroomId(Long toWorkroomId) {
        this.toWorkroomId = toWorkroomId;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public Transfer status(TransferStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public Transfer tickets(Set<Ticket> tickets) {
        this.tickets = tickets;
        return this;
    }

    public Transfer addTicket(Ticket ticket) {
        tickets.add(ticket);
        ticket.setTransfer(this);
        return this;
    }

    public Transfer removeTicket(Ticket ticket) {
        tickets.remove(ticket);
        ticket.setTransfer(null);
        return this;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Transfer transfer = (Transfer) o;
        if (transfer.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, transfer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Transfer{" +
            "id=" + id +
            ", fromWorkroomId='" + fromWorkroomId + "'" +
            ", toWorkroomId='" + toWorkroomId + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
