package com.test.CRUDTest.controller;

import com.test.CRUDTest.exception.ResourceNotFoundException;
import com.test.CRUDTest.model.Customer;
import com.test.CRUDTest.model.Detail;
import com.test.CRUDTest.model.Employee;
import com.test.CRUDTest.repository.EmployeeRepository;
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
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employee")
    public List<Employee> getAllEmployee() {
        return employeeRepository.findAll();
    }

    @GetMapping("/employee/{employeeID}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(value = "employeeID") Long employeeID)
        throws ResourceNotFoundException {
    	Employee employee = employeeRepository.findById(employeeID)
          .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeID));
        return ResponseEntity.ok().body(employee);
    }
    
    @PostMapping("/employee")
    public Employee createEmployee(@Valid @RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @PutMapping("/employee/{employeeID}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(value = "employeeID") Long employeeID,
                                                  @Valid @RequestBody Employee employeeDetails) throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeID)
        .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeID));

        employee.setLastName(employeeDetails.getLastName());
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setTitle(employeeDetails.getTitle());
        employee.setWorkPhone(employeeDetails.getWorkPhone());
        final Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/employee/{employeeID}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "employeeID") Long employeeID)
         throws ResourceNotFoundException {
        Employee employee = employeeRepository.findById(employeeID)
       .orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeID));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    @GetMapping("/employee/csv")
    public List<Employee> insertCSV(){
        String csvFilePath = "./dir/Employees.csv";

        String lineText;
        try {
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            lineReader.readLine(); // skip header line

            while ((lineText = lineReader.readLine()) != null) {
                Employee employee = new Employee();
                String[] data = lineText.split(";");
                employee.setEmployeeID(Long.valueOf(data[0]));
                employee.setFirstName(data[1]);
                employee.setLastName(data[2]);
                employee.setTitle(data[3]);
                employee.setWorkPhone(data[4]);
                employeeRepository.save(employee);
            }
            lineReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return employeeRepository.findAll();
    }
}