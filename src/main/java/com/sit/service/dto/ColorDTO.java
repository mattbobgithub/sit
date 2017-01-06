package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Color entity.
 */
public class ColorDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Integer orderNumber;


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
    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ColorDTO colorDTO = (ColorDTO) o;

        if ( ! Objects.equals(id, colorDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ColorDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", orderNumber='" + orderNumber + "'" +
            '}';
    }
}
