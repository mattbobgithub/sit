package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.Gender;

/**
 * A DTO for the AlterationGroup entity.
 */
public class AlterationGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Gender gender;


    private Set<GarmentDTO> garments = new HashSet<>();

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

    public Set<GarmentDTO> getGarments() {
        return garments;
    }

    public void setGarments(Set<GarmentDTO> garments) {
        this.garments = garments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlterationGroupDTO alterationGroupDTO = (AlterationGroupDTO) o;

        if ( ! Objects.equals(id, alterationGroupDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationGroupDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
