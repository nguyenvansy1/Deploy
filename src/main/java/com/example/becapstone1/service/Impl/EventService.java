package com.example.becapstone1.service.Impl;

import com.example.becapstone1.exception.EventNotFoundException;
import com.example.becapstone1.model.event.DataMail;
import com.example.becapstone1.model.event.Event;
import com.example.becapstone1.model.user.User;
import com.example.becapstone1.repository.IAccountRepository;
import com.example.becapstone1.repository.IEventRepository;
import com.example.becapstone1.service.IEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EventService implements IEventService {
    @Autowired
    private IEventRepository iEventRepository;

    @Autowired
    private DataMailService dataMailService;

    @Autowired
    private IAccountRepository iAccountRepository;

    @Override
    public Page<Event> getAllEvent(Integer page, Integer size) {
        Pageable paging = PageRequest.of(page, size, Sort.by("event_id").descending());
        return iEventRepository.findAll(paging);
    }

    @Override
    public void editEvent(Event event) {
        iEventRepository.save(event);
    }

    @Override
    public void addEvent(Event event) {
        try {
            DataMail dataMail = new DataMail();
            dataMail.setTo(event.getCustomer().getAccount().getEmail());
            dataMail.setSubject("Event DTU");
            Map<String, Object> props = new HashMap<>();
            props.put("username", event.getCustomer().getName());
            props.put("password", "123456");
            dataMail.setProps(props);
            dataMailService.sendMail(dataMail,"Mail");
            System.out.println("Send Mail pass");
        } catch (MessagingException exp){
            exp.printStackTrace();
        }
        String passwordEncode = new BCryptPasswordEncoder().encode("123456");
        iAccountRepository.changePassword(event.getCustomer().getAccount().getAccountId(),passwordEncode);
        iEventRepository.save(event);
    }

    @Override
    public Integer[] getDataEvent() {
        Integer[][] data = iEventRepository.getDataEvent();
        Integer[] arr = new Integer[12];
        for (int i = 1 ; i<=12; i++){
            for(int row = 0; row < data.length; row++) {
                for(int column = 0; column < data[row].length-1; column++) {
                    if (i == data[row][column]) {
                        arr[i-1] = data[row][column+1];
                    }
                }
            }
        }
        for (int i = 0 ; i<12; i++) {
            if (arr[i] == null){
                arr[i] = 0;
            }
        }
        return arr;
    }

    @Override
    public List<Event> getListEventFinished() {
        return iEventRepository.getListEventFinished();
    }

    @Override
    public List<Event> getListEventUpcoming() {
        return iEventRepository.getListEventUpcoming();
    }

    @Override
    public Event findEventById(Long id) {
        return iEventRepository.findById(id).orElseThrow(() -> new EventNotFoundException("Event by id " + id + " was not found"));
    }

    @Override
    public void deleteEventByFlag(Long id) {
        iEventRepository.deleteEventByFlag(id);
    }

    @Override
    public Event getEventCheckin(Long id) {
        return iEventRepository.getEventCheckin(id);
    }

    @Override
    public List<Event> getListEvent() {
        return iEventRepository.getListEvent();
    }
}
