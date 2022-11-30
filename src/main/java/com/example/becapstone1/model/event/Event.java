package com.example.becapstone1.model.event;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;


@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @Column(name = "event_name")
    private String name;

    @Column(name = "event_location")
    private String location;

    @Column(name = "event_content")
    private String content;

    @Column(name = "event_start_time")
    private Date startTime;

    @Column(name = "event_end_time")
    private Date endTime;

    @Column(name = "event_flag")
    private Boolean flag;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_customer_id")
    private Customer customer;

    public Event() {
    }

    public Event(Long id, String name, String location, String content, Date startTime, Date endTime, Boolean flag, Customer customer) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.flag = flag;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
