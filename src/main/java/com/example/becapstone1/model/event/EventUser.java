package com.example.becapstone1.model.event;

import com.example.becapstone1.model.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "event_user")
public class EventUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_user_id")
    private Long id;

    @Column(name = "event_user_status")
    private Boolean status;

    @Column(name = "event_time_checkin")
    private Date checkin;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public EventUser() {
    }

    public EventUser(Long id, Boolean status, Date checkin, Event event, User user) {
        this.id = id;
        this.status = status;
        this.checkin = checkin;
        this.event = event;
        this.user = user;
    }

    public EventUser(Boolean status, Date checkin, Event event, User user) {
        this.status = status;
        this.checkin = checkin;
        this.event = event;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
