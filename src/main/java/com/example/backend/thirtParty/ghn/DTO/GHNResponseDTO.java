package com.example.backend.thirtParty.ghn.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GHNResponseDTO<T> {
    private int code;
    private String message;
    @JsonProperty("data")
    private T data;

    public GHNResponseDTO(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    // Getters and Setters
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
