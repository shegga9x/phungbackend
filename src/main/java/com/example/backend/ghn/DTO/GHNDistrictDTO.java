package com.example.backend.ghn.DTO;

public class GHNDistrictDTO {
    private int districtID;
    private int provinceID;
    private String districtName;
    private String code;
    private int type;
    private int supportType;

    // Getters and Setters
    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSupportType() {
        return supportType;
    }

    public void setSupportType(int supportType) {
        this.supportType = supportType;
    }
}
