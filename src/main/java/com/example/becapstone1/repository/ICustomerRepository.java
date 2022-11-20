package com.example.becapstone1.repository;

import com.example.becapstone1.model.event.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<Customer,Long> {
}
