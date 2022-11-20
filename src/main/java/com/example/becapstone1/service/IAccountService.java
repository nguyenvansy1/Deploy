package com.example.becapstone1.service;

import com.example.becapstone1.model.account.Account;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IAccountService {
    Optional<Account> findAccountById(Long id);

    void changePassword(Long id,String password);

    Boolean existsByUsername(String username);

    Optional<Account> findByUsername(String username);

    void addVerificationCode(String code, String username);

    Account findAccountByVerificationCode(String code);

    void saveNewPassword(String password, String code);

    void sendMail(String code, Optional<Account> account);

    Optional<Account> findByUsername1(String username);

}
