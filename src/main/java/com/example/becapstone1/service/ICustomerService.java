package com.example.becapstone1.service;

import com.example.becapstone1.dto.CustomerDTO;
import com.example.becapstone1.model.event.Customer;
import com.example.becapstone1.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.io.IOException;
import java.util.List;

public interface ICustomerService {
    void addCustomer(CustomerDTO customer) throws IOException;

    List<Customer> findAll();

    Customer findByAccountId( Long id );

    Page<Customer> getAllCustomer(Integer page, Integer size);

    Page<Customer> getByName(String name, Integer page, Integer size);
}
