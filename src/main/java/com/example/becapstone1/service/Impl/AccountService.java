package com.example.becapstone1.service.Impl;

import com.example.becapstone1.model.account.Account;
import com.example.becapstone1.model.event.DataMail;
import com.example.becapstone1.model.event.Event;
import com.example.becapstone1.repository.IAccountRepository;
import com.example.becapstone1.service.IAccountService;
import com.example.becapstone1.service.IDataMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
@Service
public class AccountService implements IAccountService {
    @Autowired
    private IAccountRepository iAccountRepository;

    @Autowired
    private IDataMailService dataMailService;

    @Override
    public Optional<Account> findAccountById(Long id) {
        return iAccountRepository.findAccountById(id);
    }

    @Override
    public void changePassword(Long id, String password) {
        iAccountRepository.changePassword(id,password);
    }

    @Override
    public Boolean existsByUsername(String username) {
        return iAccountRepository.existsByUsername(username);
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return iAccountRepository.findByUsername(username);
    }

    @Override
    public void addVerificationCode(String code, String username) {
        iAccountRepository.addVerificationCode(code, username);
    }

    @Override
    public Account findAccountByVerificationCode(String code) {
        return iAccountRepository.findAccountByVerificationCode(code);
    }

    @Override
    public void saveNewPassword(String password, String code) {
        iAccountRepository.saveNewPassword(password, code);
    }

    @Override
    public void sendMail(String code, Optional<Account> account){
        try {
            DataMail dataMail = new DataMail();
            dataMail.setTo(account.get().getEmail());
            dataMail.setSubject("Activate " +account.get().getUsername());
            Map<String, Object> props = new HashMap<>();
            props.put("code", code);
            props.put("email", account.get().getEmail());
            dataMail.setProps(props);
            dataMailService.sendMail(dataMail,"ResetPassword");;
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        System.out.println("Send success!!");
    }

    @Override
    public Optional<Account> findByUsername1(String username) {
        return iAccountRepository.findByUsername1(username);
    }
}
