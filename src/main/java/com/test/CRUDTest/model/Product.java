package com.test.CRUDTest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Products")
public class Product {

    private long productID;
    private String productName;
    private double unitPrice;
    private char inStock;
    public Product() {

    }

    public Product(String productName, int unitPrice, char inStock) {
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.inStock = inStock;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ProductID", nullable = false)
    public long getProductID() {
        return productID;
    }
    public void setProductID(long productID) {
        this.productID = productID;
    }

    @Column(name = "ProductName", nullable = false)
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Column(name = "UnitPrice", nullable = false)
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "InStock", nullable = false)
    public char getInStock() {
        return inStock;
    }

    public void setInStock(char inStock) {
        this.inStock = inStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", unitPrice=" + unitPrice +
                ", inStock='" + inStock + '\'' +
                '}';
    }
}
