package com.example.backend.ghn.DTO;

import com.example.backend.util.Client;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

@Client
public class GHNDistrictDTO {
    @JsonAlias("DistrictID") // Accepts "ProvinceID" when receiving data
    @JsonProperty("districtID")
    private int districtID;
    @JsonAlias("ProvinceID") // Accepts "ProvinceID" when receiving data
    @JsonProperty("provinceID")
    private int provinceID;
    @JsonAlias("DistrictName") // Accepts "ProvinceID" when receiving data
    @JsonProperty("districtName")
    private String districtName;
    @JsonAlias("Code") // Accepts "ProvinceID" when receiving data
    @JsonProperty("code")
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
