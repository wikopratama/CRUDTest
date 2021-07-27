package com.test.CRUDTest.controller;

import com.test.CRUDTest.exception.ResourceNotFoundException;
import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.model.Detail;
import com.test.CRUDTest.model.Employee;
import com.test.CRUDTest.model.Order;
import com.test.CRUDTest.model.Product;
import com.test.CRUDTest.model.Shipping;
import com.test.CRUDTest.repository.DetailRepository;
import com.test.CRUDTest.repository.OrderRepository;
import com.test.CRUDTest.repository.ProductRepository;
import org.apache.tomcat.util.http.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.jpa.domain.Specification;
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
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class DetailController {
    Logger log = LoggerFactory.getLogger(DetailController.class);
    @Autowired
    private DetailRepository detailRepository;
    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    public DetailController(DetailRepository detailRepository, OrderRepository orderRepository, ProductRepository productRepository) {
        this.detailRepository = detailRepository;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @GetMapping("/detail")
    public List<Detail> getAllDetail() {
        return detailRepository.findAll();
    }

    @GetMapping("/detail/{orderID}")
    public ResponseEntity<Detail> getDetailById(@PathVariable(value = "orderID") Long orderid)
        throws ResourceNotFoundException {

        Detail detail = new Detail();
        List<Object[]> records = detailRepository.findDetailOrder(orderid);
        if (records.size() > 0) {
            detail.setCustComp(records.get(0)[0].toString());
            detail.setCustFName(records.get(0)[1].toString());
            detail.setCustLName(records.get(0)[2].toString());
            detail.setEmpFName(records.get(0)[3].toString());
            detail.setEmpLName(records.get(0)[4].toString());
            detail.setShipping(records.get(0)[5].toString());
            detail.setProductName(records.get(0)[6].toString());
            detail.setQuantity(Integer.parseInt(String.valueOf(records.get(0)[7])));
            detail.setUnitPrice(Double.parseDouble(String.valueOf(records.get(0)[8])));
            detail.setDiscount(Double.parseDouble(String.valueOf(records.get(0)[9])));

        }
        return ResponseEntity.ok().body(detail);
    }
    
    @PostMapping("/detail")
    public Detail createDetail(@Valid @RequestBody Detail detail) {
        return detailRepository.save(detail);
    }

    @PutMapping("/detail/{orderDetailID}")
    public ResponseEntity<Detail> updateDetail(@PathVariable(value = "orderDetailID") Long orderDetailID,
                                                  @Valid @RequestBody Detail detailDetails) throws ResourceNotFoundException {
        Detail detail = detailRepository.findById(orderDetailID)
        .orElseThrow(() -> new ResourceNotFoundException("Detail not found for this id :: " + orderDetailID));

        detail.setOrder(detailDetails.getOrder());
        detail.setProduct(detailDetails.getProduct());
        detail.setQuantity(detailDetails.getQuantity());
        detail.setUnitPrice(detailDetails.getUnitPrice());
        detail.setDiscount(detailDetails.getDiscount());
        final Detail updatedPatient = detailRepository.save(detail);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/detail/{orderDetailID}")
    public Map<String, Boolean> deleteDetail(@PathVariable(value = "orderDetailID") Long orderDetailID)
         throws ResourceNotFoundException {
        Detail detail = detailRepository.findById(orderDetailID)
       .orElseThrow(() -> new ResourceNotFoundException("Detail not found for this id :: " + orderDetailID));

        detailRepository.delete(detail);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/detail/csv")
    public List<Detail> insertCSV(){

        String csvFilePath = "./dir/OrderDetails.csv";

        String lineText;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                Detail detail = new Detail();

                String[] data = lineText.split(";");
                detail.setOrderDetailID(Long.valueOf(data[0]));
                Optional<Order> order = orderRepository.findById(Long.valueOf(data[1]));
                detail.setOrder(order.get());
                Optional<Product> product = productRepository.findById(Long.valueOf(data[2]));
                detail.setProduct(product.get());
                detail.setQuantity(Integer.valueOf(data[3]));
                detail.setUnitPrice(Double.valueOf(data[4]));
                detail.setDiscount(Double.valueOf(data[5]));
                detailRepository.save(detail);
            }
            lineReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (InvalidDataAccessApiUsageException e){
            e.printStackTrace();
        }
        return detailRepository.findAll();
    }
}