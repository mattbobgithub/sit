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
 * A PriceCategory.
 */
@Entity
@Table(name = "price_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PriceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "percentage_discount")
    private Double percentageDiscount;

    @Column(name = "amount_discount")
    private Double amountDiscount;

    @OneToMany(mappedBy = "priceCategory")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AlterationPrice> alterationPrices = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public PriceCategory description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPercentageDiscount() {
        return percentageDiscount;
    }

    public PriceCategory percentageDiscount(Double percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
        return this;
    }

    public void setPercentageDiscount(Double percentageDiscount) {
        this.percentageDiscount = percentageDiscount;
    }

    public Double getAmountDiscount() {
        return amountDiscount;
    }

    public PriceCategory amountDiscount(Double amountDiscount) {
        this.amountDiscount = amountDiscount;
        return this;
    }

    public void setAmountDiscount(Double amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public Set<AlterationPrice> getAlterationPrices() {
        return alterationPrices;
    }

    public PriceCategory alterationPrices(Set<AlterationPrice> alterationPrices) {
        this.alterationPrices = alterationPrices;
        return this;
    }

    public PriceCategory addAlterationPrice(AlterationPrice alterationPrice) {
        alterationPrices.add(alterationPrice);
        alterationPrice.setPriceCategory(this);
        return this;
    }

    public PriceCategory removeAlterationPrice(AlterationPrice alterationPrice) {
        alterationPrices.remove(alterationPrice);
        alterationPrice.setPriceCategory(null);
        return this;
    }

    public void setAlterationPrices(Set<AlterationPrice> alterationPrices) {
        this.alterationPrices = alterationPrices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PriceCategory priceCategory = (PriceCategory) o;
        if (priceCategory.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, priceCategory.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PriceCategory{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", percentageDiscount='" + percentageDiscount + "'" +
            ", amountDiscount='" + amountDiscount + "'" +
            '}';
    }
}
