package com.test.CRUDTest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Order_Details")
public class Detail {

    private long orderDetailID;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OrderID", nullable = false)
    private Order order;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductID", nullable = false)
    private Product product;
    private int quantity;
    private double unitPrice;
    private double discount;

    private String custComp;
    private String custFName;
    private String custLName;
    private String empFName;
    private String empLName;
    private String shipping;
    private String productName;

    public Detail() {

    }

    public Detail(Order order, Product product, int quantity, int unitPrice, int discount) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.discount = discount;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "OrderDetailID", nullable = false)
    public long getOrderDetailID() {
        return orderDetailID;
    }
    public void setOrderDetailID(long orderDetailID) {
        this.orderDetailID = orderDetailID;
    }

    @ManyToOne()
    @JoinColumn(name = "OrderID", nullable = false)
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @ManyToOne()
    @JoinColumn(name = "ProductID", nullable = false)
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Column(name = "Quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "UnitPrice", nullable = false)
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "Discount", nullable = false)
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getCustComp() {
        return custComp;
    }

    public void setCustComp(String custComp) {
        this.custComp = custComp;
    }

    public String getCustFName() {
        return custFName;
    }

    public void setCustFName(String custFName) {
        this.custFName = custFName;
    }

    public String getCustLName() {
        return custLName;
    }

    public void setCustLName(String custLName) {
        this.custLName = custLName;
    }

    public String getEmpFName() {
        return empFName;
    }

    public void setEmpFName(String empFName) {
        this.empFName = empFName;
    }

    public String getEmpLName() {
        return empLName;
    }

    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return "Detail{" +
                "orderDetailID=" + orderDetailID +
                ", order=" + order +
                ", product=" + product +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", discount=" + discount +
                '}';
    }
}
