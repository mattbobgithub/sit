package com.sit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A GarmentSize.
 */
@Entity
@Table(name = "garment_size")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class GarmentSize implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne
    private SizeType sizeType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public GarmentSize description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public GarmentSize sizeType(SizeType sizeType) {
        this.sizeType = sizeType;
        return this;
    }

    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GarmentSize garmentSize = (GarmentSize) o;
        if (garmentSize.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, garmentSize.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GarmentSize{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
