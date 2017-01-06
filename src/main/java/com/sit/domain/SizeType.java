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

/**
 * A SizeType.
 */
@Entity
@Table(name = "size_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SizeType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @OneToMany(mappedBy = "sizeType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Garment> garments = new HashSet<>();

    @OneToMany(mappedBy = "sizeType")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GarmentSize> garmentSizes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public SizeType description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Garment> getGarments() {
        return garments;
    }

    public SizeType garments(Set<Garment> garments) {
        this.garments = garments;
        return this;
    }

    public SizeType addGarment(Garment garment) {
        garments.add(garment);
        garment.setSizeType(this);
        return this;
    }

    public SizeType removeGarment(Garment garment) {
        garments.remove(garment);
        garment.setSizeType(null);
        return this;
    }

    public void setGarments(Set<Garment> garments) {
        this.garments = garments;
    }

    public Set<GarmentSize> getGarmentSizes() {
        return garmentSizes;
    }

    public SizeType garmentSizes(Set<GarmentSize> garmentSizes) {
        this.garmentSizes = garmentSizes;
        return this;
    }

    public SizeType addGarmentSize(GarmentSize garmentSize) {
        garmentSizes.add(garmentSize);
        garmentSize.setSizeType(this);
        return this;
    }

    public SizeType removeGarmentSize(GarmentSize garmentSize) {
        garmentSizes.remove(garmentSize);
        garmentSize.setSizeType(null);
        return this;
    }

    public void setGarmentSizes(Set<GarmentSize> garmentSizes) {
        this.garmentSizes = garmentSizes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SizeType sizeType = (SizeType) o;
        if (sizeType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sizeType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SizeType{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
