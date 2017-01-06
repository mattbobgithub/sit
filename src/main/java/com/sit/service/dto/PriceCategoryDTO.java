package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the PriceCategory entity.
 */
public class PriceCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private Double percentageDiscount;

    private Double amountDiscount;


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
    public Double getPercentageDiscount() {
        return percentageDiscount;
    }

    public void setPercentageDiscount(Double percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }
    public Double getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(Double amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PriceCategoryDTO priceCategoryDTO = (PriceCategoryDTO) o;

        if ( ! Objects.equals(id, priceCategoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PriceCategoryDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", percentageDiscount='" + percentageDiscount + "'" +
            ", amountDiscount='" + amountDiscount + "'" +
            '}';
    }
}
