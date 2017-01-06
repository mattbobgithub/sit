package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the GarmentSize entity.
 */
public class GarmentSizeDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;


    private Long sizeTypeId;
    
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

    public Long getSizeTypeId() {
        return sizeTypeId;
    }

    public void setSizeTypeId(Long sizeTypeId) {
        this.sizeTypeId = sizeTypeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GarmentSizeDTO garmentSizeDTO = (GarmentSizeDTO) o;

        if ( ! Objects.equals(id, garmentSizeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GarmentSizeDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
