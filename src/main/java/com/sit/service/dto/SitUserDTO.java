package com.sit.service.dto;

import com.sit.domain.enumeration.UserType;
import com.sit.web.rest.vm.ManagedUserVM;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the SitUser entity.
 */
public class SitUserDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String username;

    private String email;

    private UserType userType;

    private Boolean fitterIndicator;

    private Integer managerApprovalCode;


    private Long companyId;

    private Long storeId;

    private Long workroomId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    public Boolean getFitterIndicator() {
        return fitterIndicator;
    }

    public void setFitterIndicator(Boolean fitterIndicator) {
        this.fitterIndicator = fitterIndicator;
    }
    public Integer getManagerApprovalCode() {
        return managerApprovalCode;
    }

    public void setManagerApprovalCode(Integer managerApprovalCode) {
        this.managerApprovalCode = managerApprovalCode;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getWorkroomId() {
        return workroomId;
    }

    public void setWorkroomId(Long workroomId) {
        this.workroomId = workroomId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    //MTC add stateless constructor for manual DTO mapping
    public SitUserDTO(){

    }


    //MTC added constructor from ManagedUserVM for convenience
    public SitUserDTO(ManagedUserVM muvm){
        this.setId(muvm.getId());
        this.setEmail(muvm.getEmail());
        this.setUsername(muvm.getLogin());
        this.setCompanyId(muvm.getCompanyId());
        this.setUserType(muvm.getUserType());
        this.setManagerApprovalCode(muvm.getManagerApprovalCode());
        this.setFitterIndicator(muvm.getFitterIndicator());
        this.setStoreId(muvm.getStoreId());
        this.setWorkroomId(muvm.getWorkroomId());
        this.setUserId(muvm.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SitUserDTO sitUserDTO = (SitUserDTO) o;

        if ( ! Objects.equals(id, sitUserDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SitUserDTO{" +
            "id=" + id +
            ", username='" + username + "'" +
            ", email='" + email + "'" +
            ", userType='" + userType + "'" +
            ", fitterIndicator='" + fitterIndicator + "'" +
            ", managerApprovalCode='" + managerApprovalCode + "'" +
            '}';
    }
}
