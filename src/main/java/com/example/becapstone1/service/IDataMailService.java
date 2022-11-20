package com.example.becapstone1.service;

import com.example.becapstone1.model.event.DataMail;

import javax.mail.MessagingException;

public interface IDataMailService {
    void sendMail(DataMail dataMail, String templateName) throws MessagingException;
}
