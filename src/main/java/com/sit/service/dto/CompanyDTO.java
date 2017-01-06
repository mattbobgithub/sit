package com.sit.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Company entity.
 */
public class CompanyDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    private String companyDbname;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getCompanyDbname() {
        return companyDbname;
    }

    public void setCompanyDbname(String companyDbname) {
        this.companyDbname = companyDbname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO companyDTO = (CompanyDTO) o;

        if ( ! Objects.equals(id, companyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CompanyDTO{" +
            "id=" + id +
            ", description='" + description + "'" +
            ", companyDbname='" + companyDbname + "'" +
            '}';
    }
}
