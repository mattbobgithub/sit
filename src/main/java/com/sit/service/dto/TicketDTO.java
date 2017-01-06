package com.sit.service.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.PurchaseType;
import com.sit.domain.enumeration.TicketPriority;
import com.sit.domain.enumeration.WaiveCostReason;

/**
 * A DTO for the Ticket entity.
 */
public class TicketDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private ZonedDateTime dropDate;

    private ZonedDateTime promiseDate;

    private ZonedDateTime workroomDeadline;

    private String recieptId;

    private Double priceCharged;

    private PurchaseType purchaseType;

    private TicketPriority priority;

    private String completedBy;

    private ZonedDateTime completedDate;

    private Boolean waiveCostIndicator;

    private WaiveCostReason waiveCostReason;


    private Long customerId;
    
    private Long storeId;
    
    private Long workroomId;
    
    private Long transferId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public ZonedDateTime getDropDate() {
        return dropDate;
    }

    public void setDropDate(ZonedDateTime dropDate) {
        this.dropDate = dropDate;
    }
    public ZonedDateTime getPromiseDate() {
        return promiseDate;
    }

    public void setPromiseDate(ZonedDateTime promiseDate) {
        this.promiseDate = promiseDate;
    }
    public ZonedDateTime getWorkroomDeadline() {
        return workroomDeadline;
    }

    public void setWorkroomDeadline(ZonedDateTime workroomDeadline) {
        this.workroomDeadline = workroomDeadline;
    }
    public String getRecieptId() {
        return recieptId;
    }

    public void setRecieptId(String recieptId) {
        this.recieptId = recieptId;
    }
    public Double getPriceCharged() {
        return priceCharged;
    }

    public void setPriceCharged(Double priceCharged) {
        this.priceCharged = priceCharged;
    }
    public PurchaseType getPurchaseType() {
        return purchaseType;
    }

    public void setPurchaseType(PurchaseType purchaseType) {
        this.purchaseType = purchaseType;
    }
    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }
    public String getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(String completedBy) {
        this.completedBy = completedBy;
    }
    public ZonedDateTime getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(ZonedDateTime completedDate) {
        this.completedDate = completedDate;
    }
    public Boolean getWaiveCostIndicator() {
        return waiveCostIndicator;
    }

    public void setWaiveCostIndicator(Boolean waiveCostIndicator) {
        this.waiveCostIndicator = waiveCostIndicator;
    }
    public WaiveCostReason getWaiveCostReason() {
        return waiveCostReason;
    }

    public void setWaiveCostReason(WaiveCostReason waiveCostReason) {
        this.waiveCostReason = waiveCostReason;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getWorkroomId() {
        return workroomId;
    }

    public void setWorkroomId(Long workroomId) {
        this.workroomId = workroomId;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TicketDTO ticketDTO = (TicketDTO) o;

        if ( ! Objects.equals(id, ticketDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TicketDTO{" +
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
