package com.test.CRUDTest.repository;

import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}