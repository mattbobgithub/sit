package com.sit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Sku.
 */
@Entity
@Table(name = "sku")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Sku implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "sku_code", nullable = false)
    private String skuCode;

    @ManyToOne
    private GarmentSize garmentSize;

    @ManyToOne
    private Garment garment;

    @ManyToOne
    private SizeType sizeType;

    @ManyToOne
    private Color color;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Sku description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public Sku skuCode(String skuCode) {
        this.skuCode = skuCode;
        return this;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public GarmentSize getGarmentSize() {
        return garmentSize;
    }

    public Sku garmentSize(GarmentSize garmentSize) {
        this.garmentSize = garmentSize;
        return this;
    }

    public void setGarmentSize(GarmentSize garmentSize) {
        this.garmentSize = garmentSize;
    }

    public Garment getGarment() {
        return garment;
    }

    public Sku garment(Garment garment) {
        this.garment = garment;
        return this;
    }

    public void setGarment(Garment garment) {
        this.garment = garment;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public Sku sizeType(SizeType sizeType) {
        this.sizeType = sizeType;
        return this;
    }

    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }

    public Color getColor() {
        return color;
    }

    public Sku color(Color color) {
        this.color = color;
        return this;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sku sku = (Sku) o;
        if (sku.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sku.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Sku{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", skuCode='" + skuCode + "'" +
            '}';
    }
}
