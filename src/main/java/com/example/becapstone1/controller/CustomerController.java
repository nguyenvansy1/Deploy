package com.example.becapstone1.controller;

import com.example.becapstone1.dto.CustomerDTO;
import com.example.becapstone1.model.event.Customer;
import com.example.becapstone1.model.user.User;
import com.example.becapstone1.service.Impl.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * CustomerController
 *
 * <p>Version 1.0
 *
 * <p>Date: 06-09-2022
 *
 * <p>Copyright
 *
 * <p>Modification Logs:
 * DATE             AUTHOR      DESCRIPTION
 * ----------------------------------------
 * 20-09-2022       SyNguyen     Create
 */

@RestController
@RequestMapping("/api/customer")
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /** Get list customer. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customerList = customerService.findAll();
        return new ResponseEntity<>(customerList,HttpStatus.OK);
    }

    /** Add Customer. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?>addCustomer(@RequestBody CustomerDTO customer) {
        try{
            customerService.addCustomer(customer);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Get list customer page. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/listPage")
    public ResponseEntity<Page<Customer>> getAllCustomer(@RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size) {
        try {
            Page<Customer> customers= customerService.getAllCustomer(page, size);
            return new ResponseEntity<>(customers,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /** Find customer by name . */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filter")
    public ResponseEntity<Page<Customer>> getAllByName(@RequestParam("page") Integer page,
                                                             @RequestParam("size") Integer size , @RequestParam("name") String name) {
        Page<Customer> customers = customerService.getByName(name,page,size);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

}
