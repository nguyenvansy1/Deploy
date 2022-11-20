package com.example.becapstone1.model.event;

import javax.persistence.*;
import java.time.LocalDate;


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

    @Column(name = "event_date")
    private LocalDate date;

    @Column(name = "event_start_time",columnDefinition = "Time")
    private String startTime;

    @Column(name = "event_end_time",columnDefinition = "Time")
    private String endTime;

    @Column(name = "event_flag")
    private Boolean flag;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_customer_id")
    private Customer customer;

    public Event() {
    }

    public Event(Long id, String name, String location, LocalDate date,  String startTime, String endTime, Boolean flag, Customer customer,String content) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.content = content;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
