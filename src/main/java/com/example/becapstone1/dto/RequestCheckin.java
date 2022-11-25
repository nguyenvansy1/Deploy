package com.example.becapstone1.dto;

public class RequestCheckin {

    private String userId;
    private String accountId;


    public RequestCheckin() {
    }

    public RequestCheckin(String userId, String accountId) {
        this.userId = userId;
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
