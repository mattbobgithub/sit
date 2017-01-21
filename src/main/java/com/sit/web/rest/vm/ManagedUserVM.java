package com.sit.web.rest.vm;

import com.sit.domain.User;
import com.sit.domain.enumeration.UserType;
import com.sit.service.dto.SitUserDTO;
import com.sit.service.dto.UserDTO;

import javax.validation.constraints.Size;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;

    private Long id;

    private String createdBy;

    private ZonedDateTime createdDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;


    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;

    private Long sitid;

    //add SitUser props
    private Long companyId;
    private UserType userType;
    private Boolean fitterIndicator;
    private Integer managerApprovalCode;
    private Long storeId;
    private Long workroomId;



    public ManagedUserVM() {
    }

    public ManagedUserVM(User user) {
        super(user);
        this.id = user.getId();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.password = null;
    }

    public ManagedUserVM(SitUserDTO sitUserDTO) {
        //now update sitUser props
        this.id = sitUserDTO.getId();
        this.companyId = sitUserDTO.getCompanyId();
        this.userType = sitUserDTO.getUserType();
        this.fitterIndicator = sitUserDTO.getFitterIndicator();
        this.managerApprovalCode = sitUserDTO.getManagerApprovalCode();
        this.storeId = sitUserDTO.getStoreId();
        this.workroomId = sitUserDTO.getWorkroomId();
    }

    //MTC add new constructor with sitUser as added param
    public ManagedUserVM(User user, SitUserDTO sitUserDTO){
        super(user);
        this.id = user.getId();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.password = null;
        this.sitid = user.getSitid();

        //now update sitUser props
        this.companyId = sitUserDTO.getCompanyId();
        this.userType = sitUserDTO.getUserType();
        this.fitterIndicator = sitUserDTO.getFitterIndicator();
        this.managerApprovalCode = sitUserDTO.getManagerApprovalCode();
        this.storeId = sitUserDTO.getStoreId();
        this.workroomId = sitUserDTO.getWorkroomId();
    }

    public ManagedUserVM(Long id, String login, String password, String firstName, String lastName,
                         String email, boolean activated, String langKey, Set<String> authorities,
                         String createdBy, ZonedDateTime createdDate, String lastModifiedBy,
                         ZonedDateTime lastModifiedDate, Long sitid,
                         //Added SitUser props
                         Long companyId,
                         UserType userType,
                         Boolean fitterIndicator,
                         Integer managerApprovalCode,
                         Long storeId,
                         Long workroomId) {
        super(login, firstName, lastName, email, activated, langKey, authorities, sitid);
        this.id = id;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.password = password;
        this.sitid = sitid;

        //Added SitUser props
        this.companyId = companyId;
        this.userType = userType;
        this.fitterIndicator = fitterIndicator;
        this.managerApprovalCode = managerApprovalCode;
        this.storeId = storeId;
        this.workroomId = workroomId;


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getPassword() {
        return password;
    }

  //MTC added sitid from User obj
    public Long getSitid() {return sitid;}
    public void setSitid(Long sitid) { this.sitid = sitid; }


    //added SitUser Getters and Setters
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

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "id=" + id +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", password='" + password + '\'' +
            ", sitid=" + sitid +
            ", companyId=" + companyId +
            ", userType=" + userType +
            ", fitterIndicator=" + fitterIndicator +
            ", managerApprovalCode=" + managerApprovalCode +
            ", storeId=" + storeId +
            ", workroomId=" + workroomId +
            '}';
    }
}
