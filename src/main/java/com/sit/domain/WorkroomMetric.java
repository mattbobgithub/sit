package com.sit.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A WorkroomMetric.
 */
@Entity
@Table(name = "workroom_metric")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class WorkroomMetric implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "default_fitting_time_mins")
    private Integer defaultFittingTimeMins;

    @Column(name = "efficiency_percentage")
    private Double efficiencyPercentage;

    @Column(name = "utilization_percentage")
    private Double utilizationPercentage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDefaultFittingTimeMins() {
        return defaultFittingTimeMins;
    }

    public WorkroomMetric defaultFittingTimeMins(Integer defaultFittingTimeMins) {
        this.defaultFittingTimeMins = defaultFittingTimeMins;
        return this;
    }

    public void setDefaultFittingTimeMins(Integer defaultFittingTimeMins) {
        this.defaultFittingTimeMins = defaultFittingTimeMins;
    }

    public Double getEfficiencyPercentage() {
        return efficiencyPercentage;
    }

    public WorkroomMetric efficiencyPercentage(Double efficiencyPercentage) {
        this.efficiencyPercentage = efficiencyPercentage;
        return this;
    }

    public void setEfficiencyPercentage(Double efficiencyPercentage) {
        this.efficiencyPercentage = efficiencyPercentage;
    }

    public Double getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public WorkroomMetric utilizationPercentage(Double utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
        return this;
    }

    public void setUtilizationPercentage(Double utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        WorkroomMetric workroomMetric = (WorkroomMetric) o;
        if (workroomMetric.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, workroomMetric.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkroomMetric{" +
            "id=" + id +
            ", defaultFittingTimeMins='" + defaultFittingTimeMins + "'" +
            ", efficiencyPercentage='" + efficiencyPercentage + "'" +
            ", utilizationPercentage='" + utilizationPercentage + "'" +
            '}';
    }
}
