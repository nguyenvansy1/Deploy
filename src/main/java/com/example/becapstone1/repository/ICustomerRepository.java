package com.example.becapstone1.repository;

import com.example.becapstone1.model.event.Customer;
import com.example.becapstone1.model.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "select * from customer where customer_account_id = :id", nativeQuery = true)
    Customer findByAccountId(@Param("id") Long id );
}
