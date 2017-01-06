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
 * A AlterationGroup.
 */
@Entity
@Table(name = "alteration_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlterationGroup implements Serializable {

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

    @OneToMany(mappedBy = "alterationGroup")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AlterationSubGroup> alterationSubGroups = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "alteration_group_garment",
               joinColumns = @JoinColumn(name="alteration_groups_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="garments_id", referencedColumnName="ID"))
    private Set<Garment> garments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public AlterationGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Gender getGender() {
        return gender;
    }

    public AlterationGroup gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Set<AlterationSubGroup> getAlterationSubGroups() {
        return alterationSubGroups;
    }

    public AlterationGroup alterationSubGroups(Set<AlterationSubGroup> alterationSubGroups) {
        this.alterationSubGroups = alterationSubGroups;
        return this;
    }

    public AlterationGroup addAlterationSubGroup(AlterationSubGroup alterationSubGroup) {
        alterationSubGroups.add(alterationSubGroup);
        alterationSubGroup.setAlterationGroup(this);
        return this;
    }

    public AlterationGroup removeAlterationSubGroup(AlterationSubGroup alterationSubGroup) {
        alterationSubGroups.remove(alterationSubGroup);
        alterationSubGroup.setAlterationGroup(null);
        return this;
    }

    public void setAlterationSubGroups(Set<AlterationSubGroup> alterationSubGroups) {
        this.alterationSubGroups = alterationSubGroups;
    }

    public Set<Garment> getGarments() {
        return garments;
    }

    public AlterationGroup garments(Set<Garment> garments) {
        this.garments = garments;
        return this;
    }

    public AlterationGroup addGarment(Garment garment) {
        garments.add(garment);
        garment.getAlterationGroups().add(this);
        return this;
    }

    public AlterationGroup removeGarment(Garment garment) {
        garments.remove(garment);
        garment.getAlterationGroups().remove(this);
        return this;
    }

    public void setGarments(Set<Garment> garments) {
        this.garments = garments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlterationGroup alterationGroup = (AlterationGroup) o;
        if (alterationGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alterationGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationGroup{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
