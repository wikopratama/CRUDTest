package com.test.CRUDTest.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.CRUDTest.exception.ResourceNotFoundException;
import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.repository.CustomerRepository;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class CustomerController {
    Logger log = LoggerFactory.getLogger(CustomerController.class);
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customer")
    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @GetMapping("/customer/{customerID}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "customerID") Long customerID)
        throws ResourceNotFoundException {
    	Customer customer = customerRepository.findById(customerID)
          .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this customerID :: " + customerID));
        return ResponseEntity.ok().body(customer);
    }
    
    @PostMapping("/customer")
    public Customer createCustomer(@Valid @RequestBody Customer customer) {
        return customerRepository.save(customer);
    }

    @PutMapping("/customer/{customerID}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "customerID") Long customerID,
                                                  @Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
    	Customer customer = customerRepository.findById(customerID)
        .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerID));

    	customer.setCompanyName(customerDetails.getCompanyName());
        customer.setFirstName(customerDetails.getFirstName());
    	customer.setLastName(customerDetails.getLastName());
        customer.setBillingAddress(customerDetails.getBillingAddress());
        customer.setCity(customerDetails.getCity());
        customer.setState(customerDetails.getState());
        customer.setZipCode(customerDetails.getZipCode());
        customer.setEmail(customerDetails.getEmail());
        customer.setCompanyWebsite(customerDetails.getCompanyWebsite());
        customer.setPhoneNumber(customerDetails.getPhoneNumber());
        customer.setFaxNumber(customerDetails.getFaxNumber());
        customer.setShipAddress(customerDetails.getShipAddress());
        customer.setShipCity(customerDetails.getShipCity());
        customer.setShipState(customerDetails.getShipState());
        customer.setShipZipCode(customerDetails.getShipZipCode());
        customer.setShipPhoneNumber(customerDetails.getShipPhoneNumber());
        final Customer updatedCustomer = customerRepository.save(customer);
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/customer/{customerID}")
    public Map<String, Boolean> deleteCustomer(@PathVariable(value = "customerID") Long customerID)
         throws ResourceNotFoundException {
    	Customer customer = customerRepository.findById(customerID)
       .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + customerID));

    	customerRepository.delete(customer);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/customer/csv")
    public List<Customer> insertCSV(){

        String csvFilePath = "./dir/Customers.csv";

        String lineText;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                Customer customer = new Customer();
                String[] data = lineText.split(";");
                customer.setCustomerID(Long.valueOf(data[0]));
                customer.setCompanyName(data[1]);
                customer.setFirstName(data[2]);
                customer.setLastName(data[3]);
                customer.setBillingAddress(data[4]);
                customer.setCity(data[5]);
                customer.setState(data[6]);
                customer.setZipCode(data[7]);
                customer.setEmail(data[8]);
                customer.setCompanyWebsite(data[9]);
                customer.setPhoneNumber(data[10]);
                customer.setFaxNumber(data[11]);
                customer.setShipAddress(data[12]);
                customer.setShipCity(data[13]);
                customer.setShipState(data[14]);
                customer.setShipZipCode(data[15]);
                customer.setShipPhoneNumber(data[16]);
                customerRepository.save(customer);
            }
            lineReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customerRepository.findAll();
    }
}