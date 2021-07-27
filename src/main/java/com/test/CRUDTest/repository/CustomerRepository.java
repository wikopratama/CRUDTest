package com.test.CRUDTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.test.CRUDTest.model.Customer;

	@Repository
	public interface CustomerRepository extends JpaRepository<Customer, Long>{

}