package com.test.CRUDTest.controller;

import com.test.CRUDTest.exception.ResourceNotFoundException;
import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.model.Order;
import com.test.CRUDTest.model.Product;
import com.test.CRUDTest.model.Shipping;
import com.test.CRUDTest.repository.CustomerRepository;
import com.test.CRUDTest.repository.ShippingRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ShippingController {
    @Autowired
    private ShippingRepository shippingRepository;

    @GetMapping("/shipping")
    public List<Shipping> getAllShipping() {
        return shippingRepository.findAll();
    }

    @GetMapping("/shipping/{shippingMethodID}")
    public ResponseEntity<Shipping> getShippingById(@PathVariable(value = "shippingMethodID") Long shippingMethodID)
        throws ResourceNotFoundException {
        Shipping shipping = shippingRepository.findById(shippingMethodID)
          .orElseThrow(() -> new ResourceNotFoundException("Shipping not found for this id :: " + shippingMethodID));
        return ResponseEntity.ok().body(shipping);
    }
    
    @PostMapping("/shipping")
    public Shipping createShipping(@Valid @RequestBody Shipping shipping) {
        return shippingRepository.save(shipping);
    }

    @PutMapping("/shipping/{shippingMethodID}")
    public ResponseEntity<Shipping> updateShipping(@PathVariable(value = "shippingMethodID") Long shippingMethodID,
                                                  @Valid @RequestBody Shipping shippingDetails) throws ResourceNotFoundException {
        Shipping shipping = shippingRepository.findById(shippingMethodID)
        .orElseThrow(() -> new ResourceNotFoundException("Shipping not found for this id :: " + shippingMethodID));

        shipping.setShippingMethod(shippingDetails.getShippingMethod());
        final Shipping updatedShipping = shippingRepository.save(shipping);
        return ResponseEntity.ok(updatedShipping);
    }

    @DeleteMapping("/shipping/{shippingMethodID}")
    public Map<String, Boolean> deleteShipping(@PathVariable(value = "shippingMethodID") Long shippingMethodID)
         throws ResourceNotFoundException {
        Shipping shipping = shippingRepository.findById(shippingMethodID)
       .orElseThrow(() -> new ResourceNotFoundException("Shipping not found for this id :: " + shippingMethodID));

        shippingRepository.delete(shipping);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/shipping/csv")
    public List<Shipping> insertCSV(){
        String csvFilePath = "./dir/ShippingMethods.csv";

        String lineText;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                Shipping shipping = new Shipping();
                String[] data = lineText.split(";");
                shipping.setShippingMethodID(Long.valueOf(data[0]));
                shipping.setShippingMethod(data[1]);
                shippingRepository.save(shipping);
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return shippingRepository.findAll();
    }
}