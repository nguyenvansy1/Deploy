package com.example.becapstone1.dto;

public class VerifyRequest {

    private String code;

    public VerifyRequest(String code) {
        this.code = code;
    }

    public VerifyRequest() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
