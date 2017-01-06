package com.sit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.PurchaseType;

import com.sit.domain.enumeration.TicketPriority;

import com.sit.domain.enumeration.WaiveCostReason;

/**
 * A Ticket.
 */
@Entity
@Table(name = "ticket")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ticket extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "drop_date")
    private ZonedDateTime dropDate;

    @Column(name = "promise_date")
    private ZonedDateTime promiseDate;

    @Column(name = "workroom_deadline")
    private ZonedDateTime workroomDeadline;

    @Column(name = "reciept_id")
    private String recieptId;

    @Column(name = "price_charged")
    private Double priceCharged;

    @Enumerated(EnumType.STRING)
    @Column(name = "purchase_type")
    private PurchaseType purchaseType;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TicketPriority priority;

    @Column(name = "completed_by")
    private String completedBy;

    @Column(name = "completed_date")
    private ZonedDateTime completedDate;

    @Column(name = "waive_cost_indicator")
    private Boolean waiveCostIndicator;

    @Enumerated(EnumType.STRING)
    @Column(name = "waive_cost_reason")
    private WaiveCostReason waiveCostReason;

    @OneToMany(mappedBy = "ticket")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TicketAlteration> ticketAlterations = new HashSet<>();

    @OneToMany(mappedBy = "ticket")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TicketAction> ticketActions = new HashSet<>();

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Workroom workroom;

    @ManyToOne
    private Transfer transfer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDropDate() {
        return dropDate;
    }

    public Ticket dropDate(ZonedDateTime dropDate) {
        this.dropDate = dropDate;
        return this;
    }

    public void setDropDate(ZonedDateTime dropDate) {
        this.dropDate = dropDate;
    }

    public ZonedDateTime getPromiseDate() {
        return promiseDate;
    }

    public Ticket promiseDate(ZonedDateTime promiseDate) {
        this.promiseDate = promiseDate;
        return this;
    }

    public void setPromiseDate(ZonedDateTime promiseDate) {
        this.promiseDate = promiseDate;
    }

    public ZonedDateTime getWorkroomDeadline() {
        return workroomDeadline;
    }

    public Ticket workroomDeadline(ZonedDateTime workroomDeadline) {
        this.workroomDeadline = workroomDeadline;
        return this;
    }

    public void setWorkroomDeadline(ZonedDateTime workroomDeadline) {
        this.workroomDeadline = workroomDeadline;
    }

    public String getRecieptId() {
        return recieptId;
    }

    public Ticket recieptId(String recieptId) {
        this.recieptId = recieptId;
        return this;
    }

    public void setRecieptId(String recieptId) {
        this.recieptId = recieptId;
    }

    public Double getPriceCharged() {
        return priceCharged;
    }

    public Ticket priceCharged(Double priceCharged) {
        this.priceCharged = priceCharged;
        return this;
    }

    public void setPriceCharged(Double priceCharged) {
        this.priceCharged = priceCharged;
    }

    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public Ticket purchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
        return this;
    }

    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public Ticket priority(TicketPriority priority) {
        this.priority = priority;
        return this;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }

    public String getCompletedBy() {
        return completedBy;
    }

    public Ticket completedBy(String completedBy) {
        this.completedBy = completedBy;
        return this;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }

    public ZonedDateTime getCompletedDate() {
        return completedDate;
    }

    public Ticket completedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
        return this;
    }

    public void setCompletedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
    }

    public Boolean isWaiveCostIndicator() {
        return waiveCostIndicator;
    }

    public Ticket waiveCostIndicator(Boolean waiveCostIndicator) {
        this.waiveCostIndicator = waiveCostIndicator;
        return this;
    }

    public void setWaiveCostIndicator(Boolean waiveCostIndicator) {
        this.waiveCostIndicator = waiveCostIndicator;
    }

    public WaiveCostReason getWaiveCostReason() {
        return waiveCostReason;
    }

    public Ticket waiveCostReason(WaiveCostReason waiveCostReason) {
        this.waiveCostReason = waiveCostReason;
        return this;
    }

    public void setWaiveCostReason(WaiveCostReason waiveCostReason) {
        this.waiveCostReason = waiveCostReason;
    }

    public Set<TicketAlteration> getTicketAlterations() {
        return ticketAlterations;
    }

    public Ticket ticketAlterations(Set<TicketAlteration> ticketAlterations) {
        this.ticketAlterations = ticketAlterations;
        return this;
    }

    public Ticket addTicketAlteration(TicketAlteration ticketAlteration) {
        ticketAlterations.add(ticketAlteration);
        ticketAlteration.setTicket(this);
        return this;
    }

    public Ticket removeTicketAlteration(TicketAlteration ticketAlteration) {
        ticketAlterations.remove(ticketAlteration);
        ticketAlteration.setTicket(null);
        return this;
    }

    public void setTicketAlterations(Set<TicketAlteration> ticketAlterations) {
        this.ticketAlterations = ticketAlterations;
    }

    public Set<TicketAction> getTicketActions() {
        return ticketActions;
    }

    public Ticket ticketActions(Set<TicketAction> ticketActions) {
        this.ticketActions = ticketActions;
        return this;
    }

    public Ticket addTicketAction(TicketAction ticketAction) {
        ticketActions.add(ticketAction);
        ticketAction.setTicket(this);
        return this;
    }

    public Ticket removeTicketAction(TicketAction ticketAction) {
        ticketActions.remove(ticketAction);
        ticketAction.setTicket(null);
        return this;
    }

    public void setTicketActions(Set<TicketAction> ticketActions) {
        this.ticketActions = ticketActions;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Ticket customer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Store getStore() {
        return store;
    }

    public Ticket store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Workroom getWorkroom() {
        return workroom;
    }

    public Ticket workroom(Workroom workroom) {
        this.workroom = workroom;
        return this;
    }

    public void setWorkroom(Workroom workroom) {
        this.workroom = workroom;
    }

    public Transfer getTransfer() {
        return transfer;
    }

    public Ticket transfer(Transfer transfer) {
        this.transfer = transfer;
        return this;
    }

    public void setTransfer(Transfer transfer) {
        this.transfer = transfer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        if (ticket.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ticket{" +
            "id=" + id +
            ", dropDate='" + dropDate + "'" +
            ", promiseDate='" + promiseDate + "'" +
            ", workroomDeadline='" + workroomDeadline + "'" +
            ", recieptId='" + recieptId + "'" +
            ", priceCharged='" + priceCharged + "'" +
            ", purchaseType='" + purchaseType + "'" +
            ", priority='" + priority + "'" +
            ", completedBy='" + completedBy + "'" +
            ", completedDate='" + completedDate + "'" +
            ", waiveCostIndicator='" + waiveCostIndicator + "'" +
            ", waiveCostReason='" + waiveCostReason + "'" +
            '}';
    }
}
