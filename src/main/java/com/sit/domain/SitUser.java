package com.sit.domain;

import com.sit.domain.enumeration.UserType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A SitUser.
 */
@Entity
@Table(name = "sit_user")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SitUser extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "fitter_indicator")
    private Boolean fitterIndicator;

    @Column(name = "manager_approval_code")
    private Integer managerApprovalCode;

    @ManyToOne
    private Company company;

    @ManyToOne
    private Store store;

    @ManyToOne
    private Workroom workroom;

    @OneToOne
    @JoinColumn(unique = true)
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public SitUser username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public SitUser email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public SitUser userType(UserType userType) {
        this.userType = userType;
        return this;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Boolean isFitterIndicator() {
        return fitterIndicator;
    }

    public SitUser fitterIndicator(Boolean fitterIndicator) {
        this.fitterIndicator = fitterIndicator;
        return this;
    }

    public void setFitterIndicator(Boolean fitterIndicator) {
        this.fitterIndicator = fitterIndicator;
    }

    public Integer getManagerApprovalCode() {
        return managerApprovalCode;
    }

    public SitUser managerApprovalCode(Integer managerApprovalCode) {
        this.managerApprovalCode = managerApprovalCode;
        return this;
    }

    public void setManagerApprovalCode(Integer managerApprovalCode) {
        this.managerApprovalCode = managerApprovalCode;
    }

    public Company getCompany() {
        return company;
    }

    public SitUser company(Company company) {
        this.company = company;
        return this;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Store getStore() {
        return store;
    }

    public SitUser store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Workroom getWorkroom() {
        return workroom;
    }

    public SitUser workroom(Workroom workroom) {
        this.workroom = workroom;
        return this;
    }

    public void setWorkroom(Workroom workroom) {
        this.workroom = workroom;
    }

    public User getUser() {
        return user;
    }

    public SitUser user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SitUser sitUser = (SitUser) o;
        if (sitUser.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, sitUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SitUser{" +
            "id=" + id +
            ", username='" + username + "'" +
            ", email='" + email + "'" +
            ", userType='" + userType + "'" +
            ", fitterIndicator='" + fitterIndicator + "'" +
            ", managerApprovalCode='" + managerApprovalCode + "'" +
            '}';
    }
}
