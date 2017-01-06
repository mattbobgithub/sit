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

import com.sit.domain.enumeration.Gender;

/**
 * A Garment.
 */
@Entity
@Table(name = "garment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Garment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @ManyToMany(mappedBy = "garments")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AlterationGroup> alterationGroups = new HashSet<>();

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

    public Garment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Gender getGender() {
        return gender;
    }

    public Garment gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<AlterationGroup> getAlterationGroups() {
        return alterationGroups;
    }

    public Garment alterationGroups(Set<AlterationGroup> alterationGroups) {
        this.alterationGroups = alterationGroups;
        return this;
    }

    public Garment addAlterationGroup(AlterationGroup alterationGroup) {
        alterationGroups.add(alterationGroup);
        alterationGroup.getGarments().add(this);
        return this;
    }

    public Garment removeAlterationGroup(AlterationGroup alterationGroup) {
        alterationGroups.remove(alterationGroup);
        alterationGroup.getGarments().remove(this);
        return this;
    }

    public void setAlterationGroups(Set<AlterationGroup> alterationGroups) {
        this.alterationGroups = alterationGroups;
    }

    public SizeType getSizeType() {
        return sizeType;
    }

    public Garment sizeType(SizeType sizeType) {
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
        Garment garment = (Garment) o;
        if (garment.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, garment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Garment{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
