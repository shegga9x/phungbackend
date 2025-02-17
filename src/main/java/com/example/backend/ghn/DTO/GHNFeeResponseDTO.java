package com.example.backend.ghn.DTO;

public class GHNFeeResponseDTO {
    private int total;
    private int service_fee;
    private int insurance_fee;
    private int pick_station_fee;
    private int coupon_value;
    private int r2s_fee;
    private int document_return;
    private int double_check;
    private int cod_fee;
    private int pick_remote_areas_fee;
    private int deliver_remote_areas_fee;
    private int cod_failed_fee;
    private int return_again;

    // Getters and Setters
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getService_fee() {
        return service_fee;
    }

    public void setService_fee(int service_fee) {
        this.service_fee = service_fee;
    }

    public int getInsurance_fee() {
        return insurance_fee;
    }

    public void setInsurance_fee(int insurance_fee) {
        this.insurance_fee = insurance_fee;
    }

    public int getPick_station_fee() {
        return pick_station_fee;
    }

    public void setPick_station_fee(int pick_station_fee) {
        this.pick_station_fee = pick_station_fee;
    }

    public int getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(int coupon_value) {
        this.coupon_value = coupon_value;
    }

    public int getR2s_fee() {
        return r2s_fee;
    }

    public void setR2s_fee(int r2s_fee) {
        this.r2s_fee = r2s_fee;
    }

    public int getDocument_return() {
        return document_return;
    }

    public void setDocument_return(int document_return) {
        this.document_return = document_return;
    }

    public int getDouble_check() {
        return double_check;
    }

    public void setDouble_check(int double_check) {
        this.double_check = double_check;
    }

    public int getCod_fee() {
        return cod_fee;
    }

    public void setCod_fee(int cod_fee) {
        this.cod_fee = cod_fee;
    }

    public int getPick_remote_areas_fee() {
        return pick_remote_areas_fee;
    }

    public void setPick_remote_areas_fee(int pick_remote_areas_fee) {
        this.pick_remote_areas_fee = pick_remote_areas_fee;
    }

    public int getDeliver_remote_areas_fee() {
        return deliver_remote_areas_fee;
    }

    public void setDeliver_remote_areas_fee(int deliver_remote_areas_fee) {
        this.deliver_remote_areas_fee = deliver_remote_areas_fee;
    }

    public int getCod_failed_fee() {
        return cod_failed_fee;
    }

    public void setCod_failed_fee(int cod_failed_fee) {
        this.cod_failed_fee = cod_failed_fee;
    }

    public int getReturn_again() {
        return return_again;
    }

    public void setReturn_again(int return_again) {
        this.return_again = return_again;
    }
    
}