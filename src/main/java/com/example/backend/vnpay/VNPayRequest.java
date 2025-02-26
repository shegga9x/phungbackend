package com.example.backend.vnpay;

import com.example.backend.util.Client;


@Client
public class VNPayRequest {
    private int serviceId;
    private String bookId;
    private Long userId;

    public VNPayRequest(int serviceId, String bookId, Long userId) {
        this.serviceId = serviceId;
        this.bookId = bookId;
        this.userId = userId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public String getBookId() {
        return bookId;
    }

    public Long getUserId() {
        return userId;
    }

}
