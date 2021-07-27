package com.test.CRUDTest.controller;

import com.test.CRUDTest.exception.ResourceNotFoundException;
import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.model.Employee;
import com.test.CRUDTest.model.Order;
import com.test.CRUDTest.model.Product;
import com.test.CRUDTest.repository.ProductRepository;
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
import javax.validation.constraints.Null;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController @CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/product")
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @GetMapping("/product/{productID}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "productID") Long productID)
        throws ResourceNotFoundException {
        Product product = productRepository.findById(productID)
          .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productID));
        return ResponseEntity.ok().body(product);
    }
    
    @PostMapping("/product")
    public Product createProduct(@Valid @RequestBody Product product) {
        return productRepository.save(product);
    }

    @PutMapping("/product/{productID}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "productID") Long productID,
                                                  @Valid @RequestBody Product productDetails) throws ResourceNotFoundException {
        Product product = productRepository.findById(productID)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productID));

        product.setProductName(productDetails.getProductName());
        product.setUnitPrice(productDetails.getUnitPrice());
        product.setInStock(productDetails.getInStock());
        final Product updatedProduct = productRepository.save(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/product/{productID}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "productID") Long productID)
         throws ResourceNotFoundException {
        Product product = productRepository.findById(productID)
       .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id :: " + productID));

        productRepository.delete(product);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/product/csv")
    public List<Product> insertCSV(){
        String csvFilePath = "./dir/Products.csv";

        String lineText;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                Product product = new Product();
                String[] data = lineText.split(";");
                product.setProductID(Long.valueOf(data[0]));
                product.setProductName(data[1]);
                product.setUnitPrice(Double.valueOf(data[2]));
                product.setInStock(data[3].charAt(0));
                productRepository.save(product);
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return productRepository.findAll();
    }
}