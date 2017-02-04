package com.sit.service.dto;

import java.io.Serializable;


/**
 * A DTO for the Customer entity.
 */
public class CustomerAddressDTO extends AbstractAuditingDTO implements Serializable {

    private Long id;

    private String streetAddress1;

    private String streetAddress2;

    private String city;

    private String state;

    private String postal;

    private String country;

   private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreetAddress1() {
        return streetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        this.streetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "CustomerAddressDTO{" +
            "id=" + id +
            ", streetAddress1='" + streetAddress1 + '\'' +
            ", streetAddress2='" + streetAddress2 + '\'' +
            ", city='" + city + '\'' +
            ", state='" + state + '\'' +
            ", postal='" + postal + '\'' +
            ", country='" + country + '\'' +
            ", customerId=" + customerId +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerAddressDTO that = (CustomerAddressDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (streetAddress1 != null ? !streetAddress1.equals(that.streetAddress1) : that.streetAddress1 != null)
            return false;
        if (streetAddress2 != null ? !streetAddress2.equals(that.streetAddress2) : that.streetAddress2 != null)
            return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;
        if (postal != null ? !postal.equals(that.postal) : that.postal != null) return false;
        if (country != null ? !country.equals(that.country) : that.country != null) return false;
        return customerId != null ? customerId.equals(that.customerId) : that.customerId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (streetAddress1 != null ? streetAddress1.hashCode() : 0);
        result = 31 * result + (streetAddress2 != null ? streetAddress2.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (postal != null ? postal.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (customerId != null ? customerId.hashCode() : 0);
        return result;
    }
}

