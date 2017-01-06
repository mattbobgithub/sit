package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.Gender;

/**
 * A DTO for the AlterationSubGroup entity.
 */
public class AlterationSubGroupDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Gender gender;


    private Long alterationGroupId;
    
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

    public Long getAlterationGroupId() {
        return alterationGroupId;
    }

    public void setAlterationGroupId(Long alterationGroupId) {
        this.alterationGroupId = alterationGroupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlterationSubGroupDTO alterationSubGroupDTO = (AlterationSubGroupDTO) o;

        if ( ! Objects.equals(id, alterationSubGroupDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationSubGroupDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
