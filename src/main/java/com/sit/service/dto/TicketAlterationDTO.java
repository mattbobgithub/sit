package com.sit.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.Gender;

/**
 * A DTO for the TicketAlteration entity.
 */
public class TicketAlterationDTO implements Serializable {

    private Long id;

    private Integer lineNumber;

    private Gender gender;

    private Long altId;

    private String description;

    private String tagType;

    private Integer estimateTime;

    private Double price;

    private Double measurement1;

    private Double measurement2;

    private Integer quantity;

    private Boolean completeIndicator;


    private Long ticketId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Long getAltId() {
        return altId;
    }

    public void setAltId(Long altId) {
        this.altId = altId;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getTagType() {
        return tagType;
    }

    public void setTagType(String tagType) {
        this.tagType = tagType;
    }
    public Integer getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(Integer estimateTime) {
        this.estimateTime = estimateTime;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public Double getMeasurement1() {
        return measurement1;
    }

    public void setMeasurement1(Double measurement1) {
        this.measurement1 = measurement1;
    }
    public Double getMeasurement2() {
        return measurement2;
    }

    public void setMeasurement2(Double measurement2) {
        this.measurement2 = measurement2;
    }
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public Boolean getCompleteIndicator() {
        return completeIndicator;
    }

    public void setCompleteIndicator(Boolean completeIndicator) {
        this.completeIndicator = completeIndicator;
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

        TicketAlterationDTO ticketAlterationDTO = (TicketAlterationDTO) o;

        if ( ! Objects.equals(id, ticketAlterationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TicketAlterationDTO{" +
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
