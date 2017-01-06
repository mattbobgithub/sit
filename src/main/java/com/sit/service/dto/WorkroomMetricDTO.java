package com.sit.service.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the WorkroomMetric entity.
 */
public class WorkroomMetricDTO implements Serializable {

    private Long id;

    private Integer defaultFittingTimeMins;

    private Double efficiencyPercentage;

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

    public void setDefaultFittingTimeMins(Integer defaultFittingTimeMins) {
        this.defaultFittingTimeMins = defaultFittingTimeMins;
    }
    public Double getEfficiencyPercentage() {
        return efficiencyPercentage;
    }

    public void setEfficiencyPercentage(Double efficiencyPercentage) {
        this.efficiencyPercentage = efficiencyPercentage;
    }
    public Double getUtilizationPercentage() {
        return utilizationPercentage;
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

        WorkroomMetricDTO workroomMetricDTO = (WorkroomMetricDTO) o;

        if ( ! Objects.equals(id, workroomMetricDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WorkroomMetricDTO{" +
            "id=" + id +
            ", defaultFittingTimeMins='" + defaultFittingTimeMins + "'" +
            ", efficiencyPercentage='" + efficiencyPercentage + "'" +
            ", utilizationPercentage='" + utilizationPercentage + "'" +
            '}';
    }
}
