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
 * A Workroom.
 */
@Entity
@Table(name = "workroom")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Workroom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "phone")
    private String phone;

    @Column(name = "central_workroom_indicator")
    private Boolean centralWorkroomIndicator;

    @OneToOne
    @JoinColumn(unique = true)
    private WorkroomMetric workroomMetric;

    @OneToMany(mappedBy = "workroom")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SitUser> workroomUsers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Workroom description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public Workroom phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean isCentralWorkroomIndicator() {
        return centralWorkroomIndicator;
    }

    public Workroom centralWorkroomIndicator(Boolean centralWorkroomIndicator) {
        this.centralWorkroomIndicator = centralWorkroomIndicator;
        return this;
    }

    public void setCentralWorkroomIndicator(Boolean centralWorkroomIndicator) {
        this.centralWorkroomIndicator = centralWorkroomIndicator;
    }

    public WorkroomMetric getWorkroomMetric() {
        return workroomMetric;
    }

    public Workroom workroomMetric(WorkroomMetric workroomMetric) {
        this.workroomMetric = workroomMetric;
        return this;
    }

    public void setWorkroomMetric(WorkroomMetric workroomMetric) {
        this.workroomMetric = workroomMetric;
    }

    public Set<SitUser> getWorkroomUsers() {
        return workroomUsers;
    }

    public Workroom workroomUsers(Set<SitUser> sitUsers) {
        this.workroomUsers = sitUsers;
        return this;
    }

    public Workroom addWorkroomUser(SitUser sitUser) {
        workroomUsers.add(sitUser);
        sitUser.setWorkroom(this);
        return this;
    }

    public Workroom removeWorkroomUser(SitUser sitUser) {
        workroomUsers.remove(sitUser);
        sitUser.setWorkroom(null);
        return this;
    }

    public void setWorkroomUsers(Set<SitUser> sitUsers) {
        this.workroomUsers = sitUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Workroom workroom = (Workroom) o;
        if (workroom.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workroom.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Workroom{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", phone='" + phone + "'" +
            ", centralWorkroomIndicator='" + centralWorkroomIndicator + "'" +
            '}';
    }
}
