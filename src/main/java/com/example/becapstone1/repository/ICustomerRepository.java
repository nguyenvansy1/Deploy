package com.example.becapstone1.repository;

import com.example.becapstone1.model.event.Customer;
import com.example.becapstone1.model.event.Event;
import com.example.becapstone1.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ICustomerRepository extends JpaRepository<Customer,Long> {
    @Query(value = "select * from customers where customer_account_id = :id", nativeQuery = true)
    Customer findByAccountId(@Param("id") Long id );

    Page<Customer> findAll(Pageable pageable);

    @Query(value = "Select * from customers where customer_name like BINARY :name", nativeQuery = true)
    Page<Customer> findByNameContaining(@Param("name") String name, Pageable pageable);
}
