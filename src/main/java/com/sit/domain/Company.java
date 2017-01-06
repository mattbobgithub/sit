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
 * A Company.
 */
@Entity
@Table(name = "company")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Company extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "company_dbname")
    private String companyDbname;

    @OneToMany(mappedBy = "company")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SitUser> sitUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Company description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompanyDbname() {
        return companyDbname;
    }

    public Company companyDbname(String companyDbname) {
        this.companyDbname = companyDbname;
        return this;
    }

    public void setCompanyDbname(String companyDbname) {
        this.companyDbname = companyDbname;
    }

    public Set<SitUser> getSitUsers() {
        return sitUsers;
    }

    public Company sitUsers(Set<SitUser> sitUsers) {
        this.sitUsers = sitUsers;
        return this;
    }

    public Company addSitUser(SitUser sitUser) {
        sitUsers.add(sitUser);
        sitUser.setCompany(this);
        return this;
    }

    public Company removeSitUser(SitUser sitUser) {
        sitUsers.remove(sitUser);
        sitUser.setCompany(null);
        return this;
    }

    public void setSitUsers(Set<SitUser> sitUsers) {
        this.sitUsers = sitUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company company = (Company) o;
        if (company.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Company{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", companyDbname='" + companyDbname + "'" +
            '}';
    }
}
