package com.test.CRUDTest.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {

    private long orderID;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CustomerID", nullable = false)
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "EmployeeID", nullable = false)
    private Employee employee;
    private LocalDate orderDate;
    private String orderNumber;
    private LocalDate shipDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ShippingMethodID", nullable = false)
    private Shipping shipping;
    private double freightCharge;
    private double taxes;
    private char PaymentReceived;
    private String comment;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Order> detail = new ArrayList<>();

    public Order() {

    }

    public Order(Customer customer, Employee employee, LocalDate orderDate, String orderNumber, LocalDate shipDate, Shipping shipping, int freightCharge, int taxes, char paymentReceived, String comment) {
        this.customer = customer;
        this.employee = employee;
        this.orderDate = orderDate;
        this.orderNumber = orderNumber;
        this.shipDate = shipDate;
        this.shipping = shipping;
        this.freightCharge = freightCharge;
        this.taxes = taxes;
        PaymentReceived = paymentReceived;
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderID", nullable = false)
    public long getOrderID() {
        return orderID;
    }
    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    @ManyToOne()
    @JoinColumn(name = "CustomerID", nullable = false)
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne()
    @JoinColumn(name = "EmployeeID", nullable = false)
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Column(name = "OrderDate", nullable = false)
    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Column(name = "OrderNumber", nullable = false)
    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Column(name = "ShipDate")
    public LocalDate getShipDate() {
        return shipDate;
    }

    public void setShipDate(LocalDate shipDate) {
        this.shipDate = shipDate;
    }

    @ManyToOne()
    @JoinColumn(name = "ShippingMethodID", nullable = false)
    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    @Column(name = "FreightCharge")
    public double getFreightCharge() {
        return freightCharge;
    }

    public void setFreightCharge(double freightCharge) {
        this.freightCharge = freightCharge;
    }

    @Column(name = "Taxes", nullable = false)
    public double getTaxes() {
        return taxes;
    }

    public void setTaxes(double taxes) {
        this.taxes = taxes;
    }

    @Column(name = "PaymentReceived", nullable = false)
    public char getPaymentReceived() {
        return PaymentReceived;
    }

    public void setPaymentReceived(char paymentReceived) {
        PaymentReceived = paymentReceived;
    }

    @Column(name = "Comment")
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderID=" + orderID +
                ", customer=" + customer +
                ", employee=" + employee +
                ", orderDate=" + orderDate +
                ", orderNumber='" + orderNumber + '\'' +
                ", shipDate=" + shipDate +
                ", shipping=" + shipping +
                ", freightCharge=" + freightCharge +
                ", taxes=" + taxes +
                ", PaymentReceived=" + PaymentReceived +
                ", comment='" + comment + '\'' +
                '}';
    }
}
