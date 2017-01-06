package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Sku entity.
 */
public class SkuDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private String skuCode;


    private Long garmentSizeId;
    
    private Long garmentId;
    
    private Long sizeTypeId;
    
    private Long colorId;
    
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
    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public Long getGarmentSizeId() {
        return garmentSizeId;
    }

    public void setGarmentSizeId(Long garmentSizeId) {
        this.garmentSizeId = garmentSizeId;
    }

    public Long getGarmentId() {
        return garmentId;
    }

    public void setGarmentId(Long garmentId) {
        this.garmentId = garmentId;
    }

    public Long getSizeTypeId() {
        return sizeTypeId;
    }

    public void setSizeTypeId(Long sizeTypeId) {
        this.sizeTypeId = sizeTypeId;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkuDTO skuDTO = (SkuDTO) o;

        if ( ! Objects.equals(id, skuDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SkuDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", skuCode='" + skuCode + "'" +
            '}';
    }
}
