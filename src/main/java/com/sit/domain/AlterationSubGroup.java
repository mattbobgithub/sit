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
 * A AlterationSubGroup.
 */
@Entity
@Table(name = "alteration_sub_group")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlterationSubGroup implements Serializable {

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

    @ManyToOne
    private AlterationGroup alterationGroup;

    @OneToMany(mappedBy = "alterationSubGroup")
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

    public AlterationSubGroup description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Gender getGender() {
        return gender;
    }

    public AlterationSubGroup gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public AlterationGroup getAlterationGroup() {
        return alterationGroup;
    }

    public AlterationSubGroup alterationGroup(AlterationGroup alterationGroup) {
        this.alterationGroup = alterationGroup;
        return this;
    }

    public void setAlterationGroup(AlterationGroup alterationGroup) {
        this.alterationGroup = alterationGroup;
    }

    public Set<Alteration> getAlterations() {
        return alterations;
    }

    public AlterationSubGroup alterations(Set<Alteration> alterations) {
        this.alterations = alterations;
        return this;
    }

    public AlterationSubGroup addAlteration(Alteration alteration) {
        alterations.add(alteration);
        alteration.setAlterationSubGroup(this);
        return this;
    }

    public AlterationSubGroup removeAlteration(Alteration alteration) {
        alterations.remove(alteration);
        alteration.setAlterationSubGroup(null);
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
        AlterationSubGroup alterationSubGroup = (AlterationSubGroup) o;
        if (alterationSubGroup.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alterationSubGroup.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationSubGroup{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", gender='" + gender + "'" +
            '}';
    }
}
