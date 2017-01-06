package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.Gender;

/**
 * A DTO for the Garment entity.
 */
public class GarmentDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Gender gender;


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
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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

        GarmentDTO garmentDTO = (GarmentDTO) o;

        if ( ! Objects.equals(id, garmentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GarmentDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
