package com.sit.domain;

import java.io.Serializable;

/**
 * Created by colem on 2/2/2017.
 */
public abstract class Address implements Serializable {

    private String StreetAddress1;
    private String StreetAddress2;
    private String City;
    private String County;
    private String State;
    private String Postal;
    private String Country;


    public Address() {
    }

    @Override
    public String toString() {
        return "Address{" +
            "StreetAddress1='" + StreetAddress1 + '\'' +
            ", StreetAddress2='" + StreetAddress2 + '\'' +
            ", City='" + City + '\'' +
            ", County='" + County + '\'' +
            ", State='" + State + '\'' +
            ", Postal='" + Postal + '\'' +
            ", Country='" + Country + '\'' +
            '}';
    }

    public String getStreetAddress1() {
        return StreetAddress1;
    }

    public void setStreetAddress1(String streetAddress1) {
        StreetAddress1 = streetAddress1;
    }

    public String getStreetAddress2() {
        return StreetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        StreetAddress2 = streetAddress2;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getPostal() {
        return Postal;
    }

    public void setPostal(String postal) {
        Postal = postal;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }
}
