package com.example.becapstone1.service.Impl;

import com.example.becapstone1.dto.CustomerDTO;
import com.example.becapstone1.model.account.Account;
import com.example.becapstone1.model.event.Customer;
import com.example.becapstone1.model.event.DataMail;
import com.example.becapstone1.repository.IAccountRepository;
import com.example.becapstone1.repository.IAccountRoleRepository;
import com.example.becapstone1.repository.ICustomerRepository;
import com.example.becapstone1.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CustomerService implements ICustomerService {


    @Autowired
    private ICustomerRepository iCustomerRepository;

    @Autowired
    private IAccountRoleRepository iAccountRoleRepository;

    @Autowired
    private IAccountRepository iAccountRepository;
    @Override
    public void addCustomer(CustomerDTO customer) throws IOException {
        Account account = new Account(customer.getName(),customer.getEmail(),true);
        Customer customer1 = new Customer(customer.getName(),account);
        iCustomerRepository.save(customer1);
        Account account1 = iAccountRepository.findAccountByUsername1(customer.getName());
        iAccountRoleRepository.addAccountRole(account1.getAccountId());
    }

    @Override
    public List<Customer> findAll() {
        return iCustomerRepository.findAll();
    }
}
