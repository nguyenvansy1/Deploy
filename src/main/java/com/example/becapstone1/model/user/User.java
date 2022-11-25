package com.example.becapstone1.model.user;

import com.example.becapstone1.model.account.Account;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "user")
public class User  {
    @Id
    @Column(name = "user_code")
    private Long code;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_identity_card")
    private Integer identityCard;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_birth_day")
    private LocalDate birthDay;

    @Column(name = "user_gender")
    private Boolean gender;

    @Column(name = "user_since")
    private LocalDate since;

    @Column(name = "user_avatar")
    private String avatar;

    @Column(name = "user_address")
    private String address;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_course_id")
    private Course course;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_majors_id")
    private Majors majors;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_class_id")
    private Class classUser;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_account_id")
    private Account account;
    public User() {
    }

    public User(Long code, String name, Integer identityCard, String phone, LocalDate birthDay, Boolean gender, LocalDate since, String avatar, String address, Course course, Majors majors, Class classUser, Account account) {
        this.code = code;
        this.name = name;
        this.identityCard = identityCard;
        this.phone = phone;
        this.birthDay = birthDay;
        this.gender = gender;
        this.since = since;
        this.avatar = avatar;
        this.address = address;
        this.course = course;
        this.majors = majors;
        this.classUser = classUser;
        this.account = account;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(Integer identityCard) {
        this.identityCard = identityCard;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Majors getMajors() {
        return majors;
    }

    public void setMajors(Majors majors) {
        this.majors = majors;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public LocalDate getSince() {
        return since;
    }

    public void setSince(LocalDate since) {
        this.since = since;
    }

    public Class getClassUser() {
        return classUser;
    }

    public void setClassUser(Class classUser) {
        this.classUser = classUser;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
