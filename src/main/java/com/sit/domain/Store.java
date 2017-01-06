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
 * A Store.
 */
@Entity
@Table(name = "store")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "phone")
    private String phone;

    @Column(name = "default_promise_days")
    private Integer defaultPromiseDays;

    @Column(name = "default_price_category_id")
    private Long defaultPriceCategoryId;

    @OneToMany(mappedBy = "store")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SitUser> storeUsers = new HashSet<>();

    @ManyToOne
    private Workroom workroom;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Store description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public Store phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getDefaultPromiseDays() {
        return defaultPromiseDays;
    }

    public Store defaultPromiseDays(Integer defaultPromiseDays) {
        this.defaultPromiseDays = defaultPromiseDays;
        return this;
    }

    public void setDefaultPromiseDays(Integer defaultPromiseDays) {
        this.defaultPromiseDays = defaultPromiseDays;
    }

    public Long getDefaultPriceCategoryId() {
        return defaultPriceCategoryId;
    }

    public Store defaultPriceCategoryId(Long defaultPriceCategoryId) {
        this.defaultPriceCategoryId = defaultPriceCategoryId;
        return this;
    }

    public void setDefaultPriceCategoryId(Long defaultPriceCategoryId) {
        this.defaultPriceCategoryId = defaultPriceCategoryId;
    }

    public Set<SitUser> getStoreUsers() {
        return storeUsers;
    }

    public Store storeUsers(Set<SitUser> sitUsers) {
        this.storeUsers = sitUsers;
        return this;
    }

    public Store addStoreUser(SitUser sitUser) {
        storeUsers.add(sitUser);
        sitUser.setStore(this);
        return this;
    }

    public Store removeStoreUser(SitUser sitUser) {
        storeUsers.remove(sitUser);
        sitUser.setStore(null);
        return this;
    }

    public void setStoreUsers(Set<SitUser> sitUsers) {
        this.storeUsers = sitUsers;
    }

    public Workroom getWorkroom() {
        return workroom;
    }

    public Store workroom(Workroom workroom) {
        this.workroom = workroom;
        return this;
    }

    public void setWorkroom(Workroom workroom) {
        this.workroom = workroom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Store store = (Store) o;
        if (store.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, store.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Store{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", phone='" + phone + "'" +
            ", defaultPromiseDays='" + defaultPromiseDays + "'" +
            ", defaultPriceCategoryId='" + defaultPriceCategoryId + "'" +
            '}';
    }
}
