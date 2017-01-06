package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Store entity.
 */
public class StoreDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private String phone;

    private Integer defaultPromiseDays;

    private Long defaultPriceCategoryId;


    private Long workroomId;
    
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
    public Integer getDefaultPromiseDays() {
        return defaultPromiseDays;
    }

    public void setDefaultPromiseDays(Integer defaultPromiseDays) {
        this.defaultPromiseDays = defaultPromiseDays;
    }
    public Long getDefaultPriceCategoryId() {
        return defaultPriceCategoryId;
    }

    public void setDefaultPriceCategoryId(Long defaultPriceCategoryId) {
        this.defaultPriceCategoryId = defaultPriceCategoryId;
    }

    public Long getWorkroomId() {
        return workroomId;
    }

    public void setWorkroomId(Long workroomId) {
        this.workroomId = workroomId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StoreDTO storeDTO = (StoreDTO) o;

        if ( ! Objects.equals(id, storeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StoreDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", phone='" + phone + "'" +
            ", defaultPromiseDays='" + defaultPromiseDays + "'" +
            ", defaultPriceCategoryId='" + defaultPriceCategoryId + "'" +
            '}';
    }
}
