package com.test.CRUDTest.repository;

import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.model.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailRepository extends JpaRepository<Detail, Long>, JpaSpecificationExecutor<Detail> {

    public static final String detailOrder = "select c.company_name, c.first_name as cfname, c.last_name as clname, e.first_name, e.last_name, s.shipping_method, p.product_name, d.quantity, p.unit_price, d.discount from \n" +
            "customers c, employees e, orders o, products p, shipping_methods s, order_details d\n" +
            "where o.customerid = c.customerid\n" +
            "and o.employeeid = e.employeeid\n" +
            "and s.shipping_methodid = o.shipping_methodid\n" +
            "and d.orderid = o.orderid\n" +
            "and d.productid = p.productid\n" +
            "and o.orderid = :id";

    @Query(value = detailOrder, nativeQuery = true)
    public List<Object[]> findDetailOrder(
            @Param("id") Long id);
}