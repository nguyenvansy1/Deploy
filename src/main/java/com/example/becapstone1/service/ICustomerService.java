package com.example.becapstone1.service;

import com.example.becapstone1.dto.CustomerDTO;
import com.example.becapstone1.model.event.Customer;
import org.springframework.data.repository.query.Param;

import java.io.IOException;
import java.util.List;

public interface ICustomerService {
    void addCustomer(CustomerDTO customer) throws IOException;

    List<Customer> findAll();

    Customer findByAccountId( Long id );

}
