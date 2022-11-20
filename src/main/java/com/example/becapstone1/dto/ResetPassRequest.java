package com.example.becapstone1.dto;

public class ResetPassRequest {

    private String password;
    private String code;

    public ResetPassRequest(String password, String code) {
        this.password = password;
        this.code = code;
    }

    public ResetPassRequest() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
