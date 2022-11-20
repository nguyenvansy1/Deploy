package com.example.becapstone1.controller;

import com.example.becapstone1.dto.Password;
import com.example.becapstone1.dto.ResetPassRequest;
import com.example.becapstone1.dto.VerifyRequest;
import com.example.becapstone1.model.account.Account;
import com.example.becapstone1.payload.request.LoginRequest;
import com.example.becapstone1.service.Impl.AccountService;
import com.example.becapstone1.service.Impl.DataMailService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * CustomerController
 *
 * <p>Version 1.0
 *
 * <p>Date: 20-10-2022
 *
 * <p>Copyright
 *
 * <p>Modification Logs:
 * DATE             AUTHOR      DESCRIPTION
 * ----------------------------------------
 * 20-10-2022       SyNguyen     Create
 */
@RestController
@RequestMapping("/api/account")
@CrossOrigin
public class AccountController {
     @Autowired
     private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DataMailService dataMailService;

    /** Update password. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("update/password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable("id") Long id, @RequestBody Password password) {
        Optional<Account> account = accountService.findAccountById(id);
        if (!account.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            String originalPasswordEncode = account.get().getPassword();
            boolean checkPassword = passwordEncoder.matches(password.getOldPassword(), originalPasswordEncode);
            if (checkPassword) {
                if (!password.getNewPassword().equals(password.getConfirmPassword())) {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                } else {
                    String newPassWordEncode = new BCryptPasswordEncoder().encode(password.getNewPassword());
                    accountService.changePassword(id,newPassWordEncode);

                    return new ResponseEntity<>(HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> forgotPassword(@RequestBody LoginRequest loginRequest){
        System.out.println(1);
        if (accountService.existsByUsername(loginRequest.getUsername()) != null) {
            Optional<Account> user = accountService.findByUsername(loginRequest.getUsername());
            String code = RandomString.make(64);
            accountService.addVerificationCode(code, user.get().getUsername());
            String confirmUrl = "http://localhost:4200/verify-reset-password?code=" + code;
            accountService.sendMail(confirmUrl,user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/verify-password")
    public ResponseEntity<?> VerifyPassword(@RequestBody VerifyRequest code) {
        Account isVerified = accountService.findAccountByVerificationCode(code.getCode());
        System.out.println(isVerified.getVerificationCode());
        if (isVerified.getVerificationCode().equals(code.getCode())) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/do-forget-password")
    public ResponseEntity<?> doResetPassword(@RequestBody ResetPassRequest resetPassRequest) {
        System.out.println(resetPassRequest.getPassword());
        System.out.println(resetPassRequest.getCode());
        accountService.saveNewPassword(passwordEncoder.encode(resetPassRequest.getPassword()), resetPassRequest.getCode());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
