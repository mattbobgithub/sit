package com.sit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.sit.domain.enumeration.Gender;

/**
 * A TicketAlteration.
 */
@Entity
@Table(name = "ticket_alteration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TicketAlteration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "line_number")
    private Integer lineNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "alt_id")
    private Long altId;

    @Column(name = "description")
    private String description;

    @Column(name = "tag_type")
    private String tagType;

    @Column(name = "estimate_time")
    private Integer estimateTime;

    @Column(name = "price")
    private Double price;

    @Column(name = "measurement_1")
    private Double measurement1;

    @Column(name = "measurement_2")
    private Double measurement2;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "complete_indicator")
    private Boolean completeIndicator;

    @ManyToOne
    private Ticket ticket;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public TicketAlteration lineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
        return this;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public TicketAlteration gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Long getAltId() {
        return altId;
    }

    public TicketAlteration altId(Long altId) {
        this.altId = altId;
        return this;
    }

    public void setAltId(Long altId) {
        this.altId = altId;
    }

    public String getDescription() {
        return description;
    }

    public TicketAlteration description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagType() {
        return tagType;
    }

    public TicketAlteration tagType(String tagType) {
        this.tagType = tagType;
        return this;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }

    public Integer getEstimateTime() {
        return estimateTime;
    }

    public TicketAlteration estimateTime(Integer estimateTime) {
        this.estimateTime = estimateTime;
        return this;
    }

    public void setEstimateTime(Integer estimateTime) {
        this.estimateTime = estimateTime;
    }

    public Double getPrice() {
        return price;
    }

    public TicketAlteration price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMeasurement1() {
        return measurement1;
    }

    public TicketAlteration measurement1(Double measurement1) {
        this.measurement1 = measurement1;
        return this;
    }

    public void setMeasurement1(Double measurement1) {
        this.measurement1 = measurement1;
    }

    public Double getMeasurement2() {
        return measurement2;
    }

    public TicketAlteration measurement2(Double measurement2) {
        this.measurement2 = measurement2;
        return this;
    }

    public void setMeasurement2(Double measurement2) {
        this.measurement2 = measurement2;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public TicketAlteration quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean isCompleteIndicator() {
        return completeIndicator;
    }

    public TicketAlteration completeIndicator(Boolean completeIndicator) {
        this.completeIndicator = completeIndicator;
        return this;
    }

    public void setCompleteIndicator(Boolean completeIndicator) {
        this.completeIndicator = completeIndicator;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public TicketAlteration ticket(Ticket ticket) {
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
        TicketAlteration ticketAlteration = (TicketAlteration) o;
        if (ticketAlteration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, ticketAlteration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TicketAlteration{" +
            "id=" + id +
            ", lineNumber='" + lineNumber + "'" +
            ", gender='" + gender + "'" +
            ", altId='" + altId + "'" +
            ", description='" + description + "'" +
            ", tagType='" + tagType + "'" +
            ", estimateTime='" + estimateTime + "'" +
            ", price='" + price + "'" +
            ", measurement1='" + measurement1 + "'" +
            ", measurement2='" + measurement2 + "'" +
            ", quantity='" + quantity + "'" +
            ", completeIndicator='" + completeIndicator + "'" +
            '}';
    }
}
