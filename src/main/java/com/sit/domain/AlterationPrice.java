package com.sit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A AlterationPrice.
 */
@Entity
@Table(name = "alteration_price")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AlterationPrice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    private PriceCategory priceCategory;

    @ManyToOne
    private Alteration alteration;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPrice() {
        return price;
    }

    public AlterationPrice price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public PriceCategory getPriceCategory() {
        return priceCategory;
    }

    public AlterationPrice priceCategory(PriceCategory priceCategory) {
        this.priceCategory = priceCategory;
        return this;
    }

    public void setPriceCategory(PriceCategory priceCategory) {
        this.priceCategory = priceCategory;
    }

    public Alteration getAlteration() {
        return alteration;
    }

    public AlterationPrice alteration(Alteration alteration) {
        this.alteration = alteration;
        return this;
    }

    public void setAlteration(Alteration alteration) {
        this.alteration = alteration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AlterationPrice alterationPrice = (AlterationPrice) o;
        if (alterationPrice.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, alterationPrice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AlterationPrice{" +
            "id=" + id +
            ", price='" + price + "'" +
            '}';
    }
}
