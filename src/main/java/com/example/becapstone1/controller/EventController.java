package com.example.becapstone1.controller;

import com.example.becapstone1.model.event.Event;
import com.example.becapstone1.model.event.EventUser;
import com.example.becapstone1.service.Impl.EventService;
import com.example.becapstone1.service.Impl.EventUserService;
import com.example.becapstone1.service.Impl.ExcelServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * EventController
 *
 * <p>Version 1.0
 *
 * <p>Date: 06-09-2022
 *
 * <p>Copyright
 *
 * <p>Modification Logs:
 * DATE             AUTHOR      DESCRIPTION
 * ----------------------------------------
 * 22-09-2022       SyNguyen     Create
 */

@RestController
@RequestMapping("/api/event")
@CrossOrigin
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventUserService eventUserService;

    @Autowired
    private ExcelServiceImpl excelService;

    /** Get list event. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<Event>> getAllUser(@RequestParam("page") Integer page,
                                                 @RequestParam("size") Integer size) {
        Page<Event> events = eventService.getAllEvent(page, size);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /** Add event. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addEvent(@RequestBody Event event) {
        try{
            event.setFlag(true);
            eventService.addEvent(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Update event. **/
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/update")
    public ResponseEntity<?> editEvent(@RequestBody Event event) {
        try{
            eventService.editEvent(event);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Get data event. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dataEvent")
    public ResponseEntity<?> getData() {
        Integer[] data = eventService.getDataEvent();
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /** Get data event finished. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dataEventFinished")
    public ResponseEntity<?> getDataEventFinished() {
        List<Event> data = eventService.getListEventFinished();
        Integer amountEventFinished = data.size();
        return new ResponseEntity<>(amountEventFinished,HttpStatus.OK);
    }

    /** Get data event finished. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dataEventUpcoming")
    public ResponseEntity<?> getDataEventUpcoming() {
        List<Event> data = eventService.getListEventUpcoming();
        Integer amountEventUpcoming = data.size();
        return new ResponseEntity<>(amountEventUpcoming,HttpStatus.OK);
    }

    /** Get list event by user code. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/eventByUser")
    public ResponseEntity<Page<EventUser>> getEventByUser(@RequestParam("page") Integer page,
                                                     @RequestParam("size") Integer size,@RequestParam("code") Long code) {
        try {
            Page<EventUser> events = eventUserService.getListEventByUser(code,page,size);
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Get list event by user code. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/userByEvent")
    public ResponseEntity<Page<EventUser>> getUserByEvent(@RequestParam("page") Integer page,
                                                         @RequestParam("size") Integer size,@RequestParam("id") Long id) {
        Page<EventUser> users = eventUserService.getListUserByEvent(id,page,size);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /** Find event by id. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/find/{id}")
    public ResponseEntity<?> findEventById(@PathVariable("id") Long id) {
        try {
            Event event =  eventService.findEventById(id);
            return new ResponseEntity<>(event,HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    /** Export file Excel list user for event - SyNV. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/excel")
    public ResponseEntity<InputStreamResource> generateExcel(@RequestBody Long id) throws IOException {
        System.out.println(id);
        List<EventUser> eventUsers = eventUserService.getListUserByEvent1(id);
        ByteArrayInputStream bais = excelService.export(eventUsers);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition","inline;filename=eventStatistical.xlsx");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bais));
    }

    /** Delete event by flag. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteByFlag(@PathVariable("id") Long id) {
        try {
            eventService.deleteEventByFlag(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /** Student attendance by event. */
    @PostMapping("/addEventUser")
    public ResponseEntity<?> addEventUser(@RequestParam("id") Long id,
                                          @RequestParam("code") Long code) {
        try {
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            EventUser eventUser = eventUserService.getEventUserByEventAndUser(id, code);
            if (eventUser == null) {
                eventUserService.addEventUser(format.format(date),id,code);
            } else {
                eventUser.setStatus(true);
                eventUserService.addEventUser1(eventUser);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/import/{list}")
    public ResponseEntity<?> importUser(@PathVariable("list") String list) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
