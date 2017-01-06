package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.Gender;

/**
 * A DTO for the Alteration entity.
 */
public class AlterationDTO implements Serializable {

    private Long id;

    private Gender gender;

    private Boolean activeStatus;

    @NotNull
    private String shortDescription;

    private String longDescription;

    private Integer estimatedTime;

    private Double measurement1;

    private Double measurement2;

    private Integer quantity;

    private Boolean shortListInd;

    private Boolean autoDefaultInd;

    private Integer groupOrderNum;


    private Set<AlterationDisplayTypeDTO> alterationDisplayTypes = new HashSet<>();

    private Long alterationSubGroupId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
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
    public Boolean getShortListInd() {
        return shortListInd;
    }

    public void setShortListInd(Boolean shortListInd) {
        this.shortListInd = shortListInd;
    }
    public Boolean getAutoDefaultInd() {
        return autoDefaultInd;
    }

    public void setAutoDefaultInd(Boolean autoDefaultInd) {
        this.autoDefaultInd = autoDefaultInd;
    }
    public Integer getGroupOrderNum() {
        return groupOrderNum;
    }

    public void setGroupOrderNum(Integer groupOrderNum) {
        this.groupOrderNum = groupOrderNum;
    }

    public Set<AlterationDisplayTypeDTO> getAlterationDisplayTypes() {
        return alterationDisplayTypes;
    }

    public void setAlterationDisplayTypes(Set<AlterationDisplayTypeDTO> alterationDisplayTypes) {
        this.alterationDisplayTypes = alterationDisplayTypes;
    }

    public Long getAlterationSubGroupId() {
        return alterationSubGroupId;
    }

    public void setAlterationSubGroupId(Long alterationSubGroupId) {
        this.alterationSubGroupId = alterationSubGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlterationDTO alterationDTO = (AlterationDTO) o;

        if ( ! Objects.equals(id, alterationDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationDTO{" +
            "id=" + id +
            ", gender='" + gender + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", longDescription='" + longDescription + "'" +
            ", estimatedTime='" + estimatedTime + "'" +
            ", measurement1='" + measurement1 + "'" +
            ", measurement2='" + measurement2 + "'" +
            ", quantity='" + quantity + "'" +
            ", shortListInd='" + shortListInd + "'" +
            ", autoDefaultInd='" + autoDefaultInd + "'" +
            ", groupOrderNum='" + groupOrderNum + "'" +
            '}';
    }
}
