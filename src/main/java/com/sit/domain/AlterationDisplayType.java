package com.sit.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.sit.domain.enumeration.MeasurementType;

/**
 * A AlterationDisplayType.
 */
@Entity
@Table(name = "alteration_display_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlterationDisplayType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "measurement_type")
    private MeasurementType measurementType;

    @Column(name = "displayprice")
    private Boolean displayprice;

    @Column(name = "displaytime")
    private Boolean displaytime;

    @Column(name = "displayquantity")
    private Boolean displayquantity;

    @Column(name = "displaymeasurement_1")
    private Boolean displaymeasurement1;

    @Column(name = "displaymeasurement_2")
    private Boolean displaymeasurement2;

    @Column(name = "enableprice")
    private Boolean enableprice;

    @Column(name = "enabletime")
    private Boolean enabletime;

    @Column(name = "enablequantity")
    private Boolean enablequantity;

    @Column(name = "enablemeasurement_1")
    private Boolean enablemeasurement1;

    @Column(name = "enablemeasurement_2")
    private Boolean enablemeasurement2;

    @Column(name = "default_price")
    private Double defaultPrice;

    @Column(name = "default_time")
    private Integer defaultTime;

    @Column(name = "default_quantity")
    private Integer defaultQuantity;

    @Column(name = "default_measurement_1")
    private Double defaultMeasurement1;

    @Column(name = "default_measurement_2")
    private Double defaultMeasurement2;

    @Column(name = "max_measurement_1")
    private Double maxMeasurement1;

    @Column(name = "max_measurement_2")
    private Double maxMeasurement2;

    @Column(name = "min_measurement_1")
    private Double minMeasurement1;

    @Column(name = "min_measurement_2")
    private Double minMeasurement2;

    @ManyToMany(mappedBy = "alterationDisplayTypes")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Alteration> alterations = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public AlterationDisplayType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MeasurementType getMeasurementType() {
        return measurementType;
    }

    public AlterationDisplayType measurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
        return this;
    }

    public void setMeasurementType(MeasurementType measurementType) {
        this.measurementType = measurementType;
    }

    public Boolean isDisplayprice() {
        return displayprice;
    }

    public AlterationDisplayType displayprice(Boolean displayprice) {
        this.displayprice = displayprice;
        return this;
    }

    public void setDisplayprice(Boolean displayprice) {
        this.displayprice = displayprice;
    }

    public Boolean isDisplaytime() {
        return displaytime;
    }

    public AlterationDisplayType displaytime(Boolean displaytime) {
        this.displaytime = displaytime;
        return this;
    }

    public void setDisplaytime(Boolean displaytime) {
        this.displaytime = displaytime;
    }

    public Boolean isDisplayquantity() {
        return displayquantity;
    }

    public AlterationDisplayType displayquantity(Boolean displayquantity) {
        this.displayquantity = displayquantity;
        return this;
    }

    public void setDisplayquantity(Boolean displayquantity) {
        this.displayquantity = displayquantity;
    }

    public Boolean isDisplaymeasurement1() {
        return displaymeasurement1;
    }

    public AlterationDisplayType displaymeasurement1(Boolean displaymeasurement1) {
        this.displaymeasurement1 = displaymeasurement1;
        return this;
    }

    public void setDisplaymeasurement1(Boolean displaymeasurement1) {
        this.displaymeasurement1 = displaymeasurement1;
    }

    public Boolean isDisplaymeasurement2() {
        return displaymeasurement2;
    }

    public AlterationDisplayType displaymeasurement2(Boolean displaymeasurement2) {
        this.displaymeasurement2 = displaymeasurement2;
        return this;
    }

    public void setDisplaymeasurement2(Boolean displaymeasurement2) {
        this.displaymeasurement2 = displaymeasurement2;
    }

    public Boolean isEnableprice() {
        return enableprice;
    }

    public AlterationDisplayType enableprice(Boolean enableprice) {
        this.enableprice = enableprice;
        return this;
    }

    public void setEnableprice(Boolean enableprice) {
        this.enableprice = enableprice;
    }

    public Boolean isEnabletime() {
        return enabletime;
    }

    public AlterationDisplayType enabletime(Boolean enabletime) {
        this.enabletime = enabletime;
        return this;
    }

    public void setEnabletime(Boolean enabletime) {
        this.enabletime = enabletime;
    }

    public Boolean isEnablequantity() {
        return enablequantity;
    }

    public AlterationDisplayType enablequantity(Boolean enablequantity) {
        this.enablequantity = enablequantity;
        return this;
    }

    public void setEnablequantity(Boolean enablequantity) {
        this.enablequantity = enablequantity;
    }

    public Boolean isEnablemeasurement1() {
        return enablemeasurement1;
    }

    public AlterationDisplayType enablemeasurement1(Boolean enablemeasurement1) {
        this.enablemeasurement1 = enablemeasurement1;
        return this;
    }

    public void setEnablemeasurement1(Boolean enablemeasurement1) {
        this.enablemeasurement1 = enablemeasurement1;
    }

    public Boolean isEnablemeasurement2() {
        return enablemeasurement2;
    }

    public AlterationDisplayType enablemeasurement2(Boolean enablemeasurement2) {
        this.enablemeasurement2 = enablemeasurement2;
        return this;
    }

    public void setEnablemeasurement2(Boolean enablemeasurement2) {
        this.enablemeasurement2 = enablemeasurement2;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }

    public AlterationDisplayType defaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
        return this;
    }

    public void setDefaultPrice(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Integer getDefaultTime() {
        return defaultTime;
    }

    public AlterationDisplayType defaultTime(Integer defaultTime) {
        this.defaultTime = defaultTime;
        return this;
    }

    public void setDefaultTime(Integer defaultTime) {
        this.defaultTime = defaultTime;
    }

    public Integer getDefaultQuantity() {
        return defaultQuantity;
    }

    public AlterationDisplayType defaultQuantity(Integer defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
        return this;
    }

    public void setDefaultQuantity(Integer defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public Double getDefaultMeasurement1() {
        return defaultMeasurement1;
    }

    public AlterationDisplayType defaultMeasurement1(Double defaultMeasurement1) {
        this.defaultMeasurement1 = defaultMeasurement1;
        return this;
    }

    public void setDefaultMeasurement1(Double defaultMeasurement1) {
        this.defaultMeasurement1 = defaultMeasurement1;
    }

    public Double getDefaultMeasurement2() {
        return defaultMeasurement2;
    }

    public AlterationDisplayType defaultMeasurement2(Double defaultMeasurement2) {
        this.defaultMeasurement2 = defaultMeasurement2;
        return this;
    }

    public void setDefaultMeasurement2(Double defaultMeasurement2) {
        this.defaultMeasurement2 = defaultMeasurement2;
    }

    public Double getMaxMeasurement1() {
        return maxMeasurement1;
    }

    public AlterationDisplayType maxMeasurement1(Double maxMeasurement1) {
        this.maxMeasurement1 = maxMeasurement1;
        return this;
    }

    public void setMaxMeasurement1(Double maxMeasurement1) {
        this.maxMeasurement1 = maxMeasurement1;
    }

    public Double getMaxMeasurement2() {
        return maxMeasurement2;
    }

    public AlterationDisplayType maxMeasurement2(Double maxMeasurement2) {
        this.maxMeasurement2 = maxMeasurement2;
        return this;
    }

    public void setMaxMeasurement2(Double maxMeasurement2) {
        this.maxMeasurement2 = maxMeasurement2;
    }

    public Double getMinMeasurement1() {
        return minMeasurement1;
    }

    public AlterationDisplayType minMeasurement1(Double minMeasurement1) {
        this.minMeasurement1 = minMeasurement1;
        return this;
    }

    public void setMinMeasurement1(Double minMeasurement1) {
        this.minMeasurement1 = minMeasurement1;
    }

    public Double getMinMeasurement2() {
        return minMeasurement2;
    }

    public AlterationDisplayType minMeasurement2(Double minMeasurement2) {
        this.minMeasurement2 = minMeasurement2;
        return this;
    }

    public void setMinMeasurement2(Double minMeasurement2) {
        this.minMeasurement2 = minMeasurement2;
    }

    public Set<Alteration> getAlterations() {
        return alterations;
    }

    public AlterationDisplayType alterations(Set<Alteration> alterations) {
        this.alterations = alterations;
        return this;
    }

    public AlterationDisplayType addAlteration(Alteration alteration) {
        alterations.add(alteration);
        alteration.getAlterationDisplayTypes().add(this);
        return this;
    }

    public AlterationDisplayType removeAlteration(Alteration alteration) {
        alterations.remove(alteration);
        alteration.getAlterationDisplayTypes().remove(this);
        return this;
    }

    public void setAlterations(Set<Alteration> alterations) {
        this.alterations = alterations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlterationDisplayType alterationDisplayType = (AlterationDisplayType) o;
        if (alterationDisplayType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alterationDisplayType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationDisplayType{" +
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
