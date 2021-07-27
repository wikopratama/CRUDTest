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
@Table(name = "Shipping_Methods")
public class Shipping {

    private long shippingMethodID;
    private String shippingMethod;

    @OneToMany(mappedBy = "shipping", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Order> order = new ArrayList<>();

    public Shipping() {

    }

    public Shipping(String shippingMethod) {
         this.shippingMethod = shippingMethod;
    }
 
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ShippingMethodID", nullable = false)
    public long getShippingMethodID() {
        return shippingMethodID;
    }
    public void setShippingMethodID(long shippingMethodID) {
        this.shippingMethodID = shippingMethodID;
    }
 
    @Column(name = "ShippingMethod", nullable = false)
    public String getShippingMethod() {
        return shippingMethod;
    }
    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    @Override
    public String toString() {
        return "Shipping{" +
                "shippingMethodID=" + shippingMethodID +
                ", shippingMethod='" + shippingMethod + '\'' +
                '}';
    }
}
