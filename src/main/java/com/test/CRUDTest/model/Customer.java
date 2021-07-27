package com.test.CRUDTest.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Customers")
public class Customer {

    private long customerID;
    private String companyName;
    private String firstName;
    private String lastName;
    private String billingAddress;
    private String city;
    private String state;
    private String zipCode;
    private String email;
    private String companyWebsite;
    private String phoneNumber;
    private String faxNumber;
    private String shipAddress;
    private String shipCity;
    private String shipState;
    private String shipZipCode;
    private String shipPhoneNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Order> order = new ArrayList<>();

    public Customer() {
  
    }

    public Customer(String companyName, String firstName, String lastName, String billingAddress, String city, String state, String zipCode, String email, String companyWebsite, String phoneNumber, String faxNumber, String shipAddress, String shipCity, String shipState, String shipZipCode, String shipPhoneNumber) {
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.billingAddress = billingAddress;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.email = email;
        this.companyWebsite = companyWebsite;
        this.phoneNumber = phoneNumber;
        this.faxNumber = faxNumber;
        this.shipAddress = shipAddress;
        this.shipCity = shipCity;
        this.shipState = shipState;
        this.shipZipCode = shipZipCode;
        this.shipPhoneNumber = shipPhoneNumber;
    }

    @Id
    @Column(name = "CustomerID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long getCustomerID() {
        return customerID;
    }
    public void setCustomerID(long customerID) {
        this.customerID = customerID;
    }
 
    @Column(name = "FirstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "CompanyName", nullable = false)
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "LastName", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "BillingAddress", nullable = false)
    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Column(name = "City", nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "State", nullable = false)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "ZipCode", nullable = false)
    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @Column(name = "Email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "CompanyWebsite", nullable = false)
    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    @Column(name = "PhoneNumber", nullable = false)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(name = "FaxNumber", nullable = false)
    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    @Column(name = "ShipAddress", nullable = false)
    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    @Column(name = "ShipCity", nullable = false)
    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    @Column(name = "ShipState", nullable = false)
    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    @Column(name = "ShipZipCode", nullable = false)
    public String getShipZipCode() {
        return shipZipCode;
    }

    public void setShipZipCode(String shipZipCode) {
        this.shipZipCode = shipZipCode;
    }

    @Column(name = "ShipPhoneNumber", nullable = false)
    public String getShipPhoneNumber() {
        return shipPhoneNumber;
    }

    public void setShipPhoneNumber(String shipPhoneNumber) {
        this.shipPhoneNumber = shipPhoneNumber;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerID=" + customerID +
                ", companyName='" + companyName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", email='" + email + '\'' +
                ", companyWebsite='" + companyWebsite + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", faxNumber='" + faxNumber + '\'' +
                ", shipAddress='" + shipAddress + '\'' +
                ", shipCity='" + shipCity + '\'' +
                ", shipState='" + shipState + '\'' +
                ", shipZipCode='" + shipZipCode + '\'' +
                ", shipPhoneNumber='" + shipPhoneNumber + '\'' +
                '}';
    }
}
