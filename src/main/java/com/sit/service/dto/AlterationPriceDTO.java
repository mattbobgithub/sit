package com.sit.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the AlterationPrice entity.
 */
public class AlterationPriceDTO implements Serializable {

    private Long id;

    private Double price;


    private Long priceCategoryId;
    
    private Long alterationId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getPriceCategoryId() {
        return priceCategoryId;
    }

    public void setPriceCategoryId(Long priceCategoryId) {
        this.priceCategoryId = priceCategoryId;
    }

    public Long getAlterationId() {
        return alterationId;
    }

    public void setAlterationId(Long alterationId) {
        this.alterationId = alterationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlterationPriceDTO alterationPriceDTO = (AlterationPriceDTO) o;

        if ( ! Objects.equals(id, alterationPriceDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationPriceDTO{" +
            "id=" + id +
            ", price='" + price + "'" +
            '}';
    }
}
