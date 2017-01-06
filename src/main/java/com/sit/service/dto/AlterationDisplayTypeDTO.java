package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.MeasurementType;

/**
 * A DTO for the AlterationDisplayType entity.
 */
public class AlterationDisplayTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private MeasurementType measurementType;

    private Boolean displayprice;

    private Boolean displaytime;

    private Boolean displayquantity;

    private Boolean displaymeasurement1;

    private Boolean displaymeasurement2;

    private Boolean enableprice;

    private Boolean enabletime;

    private Boolean enablequantity;

    private Boolean enablemeasurement1;

    private Boolean enablemeasurement2;

    private Double defaultPrice;

    private Integer defaultTime;

    private Integer defaultQuantity;

    private Double defaultMeasurement1;

    private Double defaultMeasurement2;

    private Double maxMeasurement1;

    private Double maxMeasurement2;

    private Double minMeasurement1;

    private Double minMeasurement2;


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
    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }
    public Boolean getDisplayprice() {
        return displayprice;
    }

    public void setDisplayprice(Boolean displayprice) {
        this.displayprice = displayprice;
    }
    public Boolean getDisplaytime() {
        return displaytime;
    }

    public void setDisplaytime(Boolean displaytime) {
        this.displaytime = displaytime;
    }
    public Boolean getDisplayquantity() {
        return displayquantity;
    }

    public void setDisplayquantity(Boolean displayquantity) {
        this.displayquantity = displayquantity;
    }
    public Boolean getDisplaymeasurement1() {
        return displaymeasurement1;
    }

    public void setDisplaymeasurement1(Boolean displaymeasurement1) {
        this.displaymeasurement1 = displaymeasurement1;
    }
    public Boolean getDisplaymeasurement2() {
        return displaymeasurement2;
    }

    public void setDisplaymeasurement2(Boolean displaymeasurement2) {
        this.displaymeasurement2 = displaymeasurement2;
    }
    public Boolean getEnableprice() {
        return enableprice;
    }

    public void setEnableprice(Boolean enableprice) {
        this.enableprice = enableprice;
    }
    public Boolean getEnabletime() {
        return enabletime;
    }

    public void setEnabletime(Boolean enabletime) {
        this.enabletime = enabletime;
    }
    public Boolean getEnablequantity() {
        return enablequantity;
    }

    public void setEnablequantity(Boolean enablequantity) {
        this.enablequantity = enablequantity;
    }
    public Boolean getEnablemeasurement1() {
        return enablemeasurement1;
    }

    public void setEnablemeasurement1(Boolean enablemeasurement1) {
        this.enablemeasurement1 = enablemeasurement1;
    }
    public Boolean getEnablemeasurement2() {
        return enablemeasurement2;
    }

    public void setEnablemeasurement2(Boolean enablemeasurement2) {
        this.enablemeasurement2 = enablemeasurement2;
    }
    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }
    public Integer getDefaultTime() {
        return defaultTime;
    }

    public void setDefaultTime(Integer defaultTime) {
        this.defaultTime = defaultTime;
    }
    public Integer getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(Integer defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }
    public Double getDefaultMeasurement1() {
        return defaultMeasurement1;
    }

    public void setDefaultMeasurement1(Double defaultMeasurement1) {
        this.defaultMeasurement1 = defaultMeasurement1;
    }
    public Double getDefaultMeasurement2() {
        return defaultMeasurement2;
    }

    public void setDefaultMeasurement2(Double defaultMeasurement2) {
        this.defaultMeasurement2 = defaultMeasurement2;
    }
    public Double getMaxMeasurement1() {
        return maxMeasurement1;
    }

    public void setMaxMeasurement1(Double maxMeasurement1) {
        this.maxMeasurement1 = maxMeasurement1;
    }
    public Double getMaxMeasurement2() {
        return maxMeasurement2;
    }

    public void setMaxMeasurement2(Double maxMeasurement2) {
        this.maxMeasurement2 = maxMeasurement2;
    }
    public Double getMinMeasurement1() {
        return minMeasurement1;
    }

    public void setMinMeasurement1(Double minMeasurement1) {
        this.minMeasurement1 = minMeasurement1;
    }
    public Double getMinMeasurement2() {
        return minMeasurement2;
    }

    public void setMinMeasurement2(Double minMeasurement2) {
        this.minMeasurement2 = minMeasurement2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AlterationDisplayTypeDTO alterationDisplayTypeDTO = (AlterationDisplayTypeDTO) o;

        if ( ! Objects.equals(id, alterationDisplayTypeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationDisplayTypeDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", measurementType='" + measurementType + "'" +
            ", displayprice='" + displayprice + "'" +
            ", displaytime='" + displaytime + "'" +
            ", displayquantity='" + displayquantity + "'" +
            ", displaymeasurement1='" + displaymeasurement1 + "'" +
            ", displaymeasurement2='" + displaymeasurement2 + "'" +
            ", enableprice='" + enableprice + "'" +
            ", enabletime='" + enabletime + "'" +
            ", enablequantity='" + enablequantity + "'" +
            ", enablemeasurement1='" + enablemeasurement1 + "'" +
            ", enablemeasurement2='" + enablemeasurement2 + "'" +
            ", defaultPrice='" + defaultPrice + "'" +
            ", defaultTime='" + defaultTime + "'" +
            ", defaultQuantity='" + defaultQuantity + "'" +
            ", defaultMeasurement1='" + defaultMeasurement1 + "'" +
            ", defaultMeasurement2='" + defaultMeasurement2 + "'" +
            ", maxMeasurement1='" + maxMeasurement1 + "'" +
            ", maxMeasurement2='" + maxMeasurement2 + "'" +
            ", minMeasurement1='" + minMeasurement1 + "'" +
            ", minMeasurement2='" + minMeasurement2 + "'" +
            '}';
    }
}
