package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Workroom entity.
 */
public class WorkroomDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private String phone;

    private Boolean centralWorkroomIndicator;


    private Long workroomMetricId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Boolean getCentralWorkroomIndicator() {
        return centralWorkroomIndicator;
    }

    public void setCentralWorkroomIndicator(Boolean centralWorkroomIndicator) {
        this.centralWorkroomIndicator = centralWorkroomIndicator;
    }

    public Long getWorkroomMetricId() {
        return workroomMetricId;
    }

    public void setWorkroomMetricId(Long workroomMetricId) {
        this.workroomMetricId = workroomMetricId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkroomDTO workroomDTO = (WorkroomDTO) o;

        if ( ! Objects.equals(id, workroomDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkroomDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", phone='" + phone + "'" +
            ", centralWorkroomIndicator='" + centralWorkroomIndicator + "'" +
            '}';
    }
}
