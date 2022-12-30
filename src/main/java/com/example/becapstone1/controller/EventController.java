package com.example.becapstone1.controller;

import com.example.becapstone1.dto.RequestCheckin;
import com.example.becapstone1.model.event.Customer;
import com.example.becapstone1.model.event.Event;
import com.example.becapstone1.model.event.EventUser;
import com.example.becapstone1.model.user.User;
import com.example.becapstone1.service.Impl.*;
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

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;


    /** Get list event. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list")
    public ResponseEntity<Page<Event>> getListEvent(@RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size) {
        Page<Event> events = eventService.getAllEvent(page, size);
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /** Get list event 2. */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list2")
    public ResponseEntity<List<Event>> getListEvent2() {
        List<Event> events = eventService.getListEvent();
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

    /** Get list user by event id. */
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
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/addEventUser")
    public ResponseEntity<?> addEventUser(@RequestBody RequestCheckin checkin) {
        try {
            User user = userService.findUserByCode(Long.parseLong(checkin.getUserId()));
            if (user != null) {
                Customer customer = customerService.findByAccountId(Long.parseLong(checkin.getAccountId()));
                Event event = eventService.getEventCheckin(customer.getId());
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                EventUser eventUser = eventUserService.getEventUserByEventAndUser(event.getId(), Long.parseLong(checkin.getUserId()));
                System.out.println(eventUser);
                if (eventUser == null) {
                    eventUserService.addEventUser(format.format(date),event.getId(), Long.parseLong(checkin.getUserId()));
                } else {
                    if (eventUser.getStatus() == false)
                        eventUser.setStatus(true);
                    eventUserService.addEventUser1(eventUser);
                }
                return new ResponseEntity<>(user,HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /** Get list user by account id ADROID. */
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    @GetMapping("/userByAccount")
    public ResponseEntity<List<User>> getUserByEvent1 (@RequestParam("accountId") Long id) {
        try {
            Customer customer = customerService.findByAccountId(id);
            System.out.println(customer);
            Event event = eventService.getEventCheckin(customer.getId());
            List<User> users = userService.getListUserByEvent(event.getId());
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filterDay")
    public ResponseEntity<?> filterDay() {
        List<EventUser> userList = eventUserService.filterDay();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filterMonth")
    public ResponseEntity<?> filterMonth() {
        List<EventUser> userList = eventUserService.filterMonth();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/filterYear")
    public ResponseEntity<?> filterYear() {
        List<EventUser> userList = eventUserService.filterYear();
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/import/{path}/{id}")
    public ResponseEntity<?> importUser(@PathVariable("path") String path ,@PathVariable("id") String id) {
        try {
            System.out.println(id);
            System.out.println(path);
            excelService.importData(path, id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
