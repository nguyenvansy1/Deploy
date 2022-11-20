package com.example.becapstone1.service;

import com.example.becapstone1.model.event.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IEventService {
    Page<Event> getAllEvent(Integer page, Integer size);

    void editEvent(Event event);

    void addEvent(Event event);

    Integer[] getDataEvent();

    List<Event> getListEventFinished();

    List<Event> getListEventUpcoming();

    Event findEventById(Long id);

    void deleteEventByFlag( Long id);
}
