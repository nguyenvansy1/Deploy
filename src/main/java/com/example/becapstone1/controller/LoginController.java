package com.example.becapstone1.controller;

import com.example.becapstone1.model.account.Account;
import com.example.becapstone1.payload.request.LoginRequest;
import com.example.becapstone1.payload.response.JwtResponse;
import com.example.becapstone1.security.jwt.JwtServiceImpl;
import com.example.becapstone1.service.Impl.AccountService;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    private JwtServiceImpl jwtService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/auth/login")
    public JwtResponse createJwtToken(@RequestBody LoginRequest loginRequest) throws Exception {
        return jwtService.createJwtToken(loginRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> forgotPassword(@RequestBody LoginRequest loginRequest){
        if (accountService.existsByUsername(loginRequest.getUsername()) != null) {
            Optional<Account> account = accountService.findByUsername1(loginRequest.getUsername());
            String originalPasswordEncode = account.get().getPassword();
            boolean checkPassword = passwordEncoder.matches(loginRequest.getPassword(), originalPasswordEncode);
            if (checkPassword) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
