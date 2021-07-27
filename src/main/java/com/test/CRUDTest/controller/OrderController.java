package com.test.CRUDTest.controller;

import com.test.CRUDTest.exception.ResourceNotFoundException;
import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.model.Detail;
import com.test.CRUDTest.model.Employee;
import com.test.CRUDTest.model.Order;
import com.test.CRUDTest.model.Shipping;
import com.test.CRUDTest.repository.CustomerRepository;
import com.test.CRUDTest.repository.EmployeeRepository;
import com.test.CRUDTest.repository.OrderRepository;
import com.test.CRUDTest.repository.ShippingRepository;
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

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class OrderController {
    Logger log = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private EmployeeRepository employeeRepository;
    private ShippingRepository shippingRepository;

    public OrderController(OrderRepository orderRepository, CustomerRepository customerRepository, EmployeeRepository employeeRepository, ShippingRepository shippingRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.employeeRepository = employeeRepository;
        this.shippingRepository = shippingRepository;
    }

    @GetMapping("/order")
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @GetMapping("/order/{orderID}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "orderID") Long orderID)
        throws ResourceNotFoundException {
    	Order order = orderRepository.findById(orderID)
          .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderID));
        return ResponseEntity.ok().body(order);
    }
    
    @PostMapping("/order")
    public Order createOrder(@Valid @RequestBody Order order) {
        return orderRepository.save(order);
    }

    @PutMapping("/order/{orderID}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "orderID") Long orderID,
                                                  @Valid @RequestBody Order orderDetails) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderID)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderID));

        order.setCustomer(orderDetails.getCustomer());
        order.setEmployee(orderDetails.getEmployee());
        order.setOrderDate(orderDetails.getOrderDate());
        order.setOrderNumber(orderDetails.getOrderNumber());
        order.setShipDate(orderDetails.getShipDate());
        order.setShipping(orderDetails.getShipping());
        order.setFreightCharge(orderDetails.getFreightCharge());
        order.setTaxes(orderDetails.getTaxes());
        order.setPaymentReceived(orderDetails.getPaymentReceived());
        order.setComment(orderDetails.getComment());
        final Order updatedOrder = orderRepository.save(order);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/order/{orderID}")
    public Map<String, Boolean> deleteOrder(@PathVariable(value = "orderID") Long orderID)
         throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderID)
       .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + orderID));

        orderRepository.delete(order);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/order/csv")
    public List<Order> insertCSV(){
        String csvFilePath = "./dir/Orders.csv";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String lineText;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                Order order = new Order();
                String[] data = lineText.split(";");
                order.setOrderID(Long.valueOf(data[0]));
                Optional<Customer> customer = customerRepository.findById(Long.valueOf(data[1]));
                order.setCustomer(customer.get());
                Optional<Employee> employee = employeeRepository.findById(Long.valueOf(data[2]));
                order.setEmployee(employee.get());
                order.setOrderDate(LocalDate.parse(data[3], formatter));
                order.setOrderNumber(data[4]);
                if(!data[5].isEmpty()) {
                order.setShipDate(LocalDate.parse(data[5], formatter));
                }
                Optional<Shipping> shipping = shippingRepository.findById(Long.valueOf(data[6]));
                order.setShipping(shipping.get());
                if(!data[7].isEmpty()) {
                    order.setFreightCharge(Double.valueOf(data[7]));
                }
                order.setTaxes(Double.valueOf(data[8]));
                order.setPaymentReceived(data[9].charAt(0));
                if(data.length>10) {
                    order.setComment(data[10]);
                }
                orderRepository.save(order);
            }
            lineReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return orderRepository.findAll();
    }
}