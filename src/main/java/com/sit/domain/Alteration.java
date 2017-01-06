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
 * A Alteration.
 */
@Entity
@Table(name = "alteration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Alteration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "active_status")
    private Boolean activeStatus;

    @NotNull
    @Column(name = "short_description", nullable = false)
    private String shortDescription;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "estimated_time")
    private Integer estimatedTime;

    @Column(name = "measurement_1")
    private Double measurement1;

    @Column(name = "measurement_2")
    private Double measurement2;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "short_list_ind")
    private Boolean shortListInd;

    @Column(name = "auto_default_ind")
    private Boolean autoDefaultInd;

    @Column(name = "group_order_num")
    private Integer groupOrderNum;

    @OneToMany(mappedBy = "alteration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AlterationPrice> alterationPrices = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "alteration_alteration_display_type",
               joinColumns = @JoinColumn(name="alterations_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="alteration_display_types_id", referencedColumnName="ID"))
    private Set<AlterationDisplayType> alterationDisplayTypes = new HashSet<>();

    @ManyToOne
    private AlterationSubGroup alterationSubGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gender getGender() {
        return gender;
    }

    public Alteration gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean isActiveStatus() {
        return activeStatus;
    }

    public Alteration activeStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
        return this;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Alteration shortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
        return this;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public Alteration longDescription(String longDescription) {
        this.longDescription = longDescription;
        return this;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Integer getEstimatedTime() {
        return estimatedTime;
    }

    public Alteration estimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
        return this;
    }

    public void setEstimatedTime(Integer estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Double getMeasurement1() {
        return measurement1;
    }

    public Alteration measurement1(Double measurement1) {
        this.measurement1 = measurement1;
        return this;
    }

    public void setMeasurement1(Double measurement1) {
        this.measurement1 = measurement1;
    }

    public Double getMeasurement2() {
        return measurement2;
    }

    public Alteration measurement2(Double measurement2) {
        this.measurement2 = measurement2;
        return this;
    }

    public void setMeasurement2(Double measurement2) {
        this.measurement2 = measurement2;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Alteration quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Boolean isShortListInd() {
        return shortListInd;
    }

    public Alteration shortListInd(Boolean shortListInd) {
        this.shortListInd = shortListInd;
        return this;
    }

    public void setShortListInd(Boolean shortListInd) {
        this.shortListInd = shortListInd;
    }

    public Boolean isAutoDefaultInd() {
        return autoDefaultInd;
    }

    public Alteration autoDefaultInd(Boolean autoDefaultInd) {
        this.autoDefaultInd = autoDefaultInd;
        return this;
    }

    public void setAutoDefaultInd(Boolean autoDefaultInd) {
        this.autoDefaultInd = autoDefaultInd;
    }

    public Integer getGroupOrderNum() {
        return groupOrderNum;
    }

    public Alteration groupOrderNum(Integer groupOrderNum) {
        this.groupOrderNum = groupOrderNum;
        return this;
    }

    public void setGroupOrderNum(Integer groupOrderNum) {
        this.groupOrderNum = groupOrderNum;
    }

    public Set<AlterationPrice> getAlterationPrices() {
        return alterationPrices;
    }

    public Alteration alterationPrices(Set<AlterationPrice> alterationPrices) {
        this.alterationPrices = alterationPrices;
        return this;
    }

    public Alteration addAlterationPrice(AlterationPrice alterationPrice) {
        alterationPrices.add(alterationPrice);
        alterationPrice.setAlteration(this);
        return this;
    }

    public Alteration removeAlterationPrice(AlterationPrice alterationPrice) {
        alterationPrices.remove(alterationPrice);
        alterationPrice.setAlteration(null);
        return this;
    }

    public void setAlterationPrices(Set<AlterationPrice> alterationPrices) {
        this.alterationPrices = alterationPrices;
    }

    public Set<AlterationDisplayType> getAlterationDisplayTypes() {
        return alterationDisplayTypes;
    }

    public Alteration alterationDisplayTypes(Set<AlterationDisplayType> alterationDisplayTypes) {
        this.alterationDisplayTypes = alterationDisplayTypes;
        return this;
    }

    public Alteration addAlterationDisplayType(AlterationDisplayType alterationDisplayType) {
        alterationDisplayTypes.add(alterationDisplayType);
        alterationDisplayType.getAlterations().add(this);
        return this;
    }

    public Alteration removeAlterationDisplayType(AlterationDisplayType alterationDisplayType) {
        alterationDisplayTypes.remove(alterationDisplayType);
        alterationDisplayType.getAlterations().remove(this);
        return this;
    }

    public void setAlterationDisplayTypes(Set<AlterationDisplayType> alterationDisplayTypes) {
        this.alterationDisplayTypes = alterationDisplayTypes;
    }

    public AlterationSubGroup getAlterationSubGroup() {
        return alterationSubGroup;
    }

    public Alteration alterationSubGroup(AlterationSubGroup alterationSubGroup) {
        this.alterationSubGroup = alterationSubGroup;
        return this;
    }

    public void setAlterationSubGroup(AlterationSubGroup alterationSubGroup) {
        this.alterationSubGroup = alterationSubGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Alteration alteration = (Alteration) o;
        if (alteration.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alteration.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Alteration{" +
            "id=" + id +
            ", gender='" + gender + "'" +
            ", activeStatus='" + activeStatus + "'" +
            ", shortDescription='" + shortDescription + "'" +
            ", longDescription='" + longDescription + "'" +
            ", estimatedTime='" + estimatedTime + "'" +
            ", measurement1='" + measurement1 + "'" +
            ", measurement2='" + measurement2 + "'" +
            ", quantity='" + quantity + "'" +
            ", shortListInd='" + shortListInd + "'" +
            ", autoDefaultInd='" + autoDefaultInd + "'" +
            ", groupOrderNum='" + groupOrderNum + "'" +
            '}';
    }
}
