package com.example.becapstone1.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) {
        String passwordEncode = new BCryptPasswordEncoder().encode("Ssy123456789@");
        System.out.println("Password: " + passwordEncode );
//        Date date = new Date();
//        SimpleDateFormat format = new SimpleDateFormat(
//                "yyyy-MM-dd HH:mm:ss");
//        System.out.println(format.format(date));
//          convert(1.5);
    }
}
