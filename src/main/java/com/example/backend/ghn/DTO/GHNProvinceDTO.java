package com.example.backend.ghn.DTO;

import com.example.backend.util.Client;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

@Client
public class GHNProvinceDTO {
    @JsonAlias("ProvinceID") // Accepts "ProvinceID" when receiving data
    @JsonProperty("provinceID")
    private int provinceID;
    @JsonAlias("ProvinceName") // Accepts "ProvinceID" when receiving data
    @JsonProperty("provinceName")
    private String provinceName;
    private String code;

    public GHNProvinceDTO(int provinceID, String provinceName, String code) {
        this.provinceID = provinceID;
        this.provinceName = provinceName;
        this.code = code;
    }

    // Getters and Setters
    public int getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(int provinceID) {
        this.provinceID = provinceID;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
