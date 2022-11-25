package com.example.becapstone1.service;

import com.example.becapstone1.model.event.EventUser;
import com.example.becapstone1.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IEventUserService {

    Page<EventUser> getListEventByUser(Long id, Integer page, Integer size);

    Page<EventUser> getListUserByEvent(Long id, Integer page, Integer size);

    List<EventUser> getListUserByEvent1(Long id);

    EventUser getEventUserByEventAndUser( Long idEvent,  Long idUser);

    void addEventUser(String time, Long id, Long code);

    void addEventUser1(EventUser eventUser);

    List<EventUser> filterDay();

    List<EventUser> filterMonth();

    List<EventUser> filterYear();
}
