package com.test.CRUDTest.repository;

import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}