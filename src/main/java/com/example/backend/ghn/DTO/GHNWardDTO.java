package com.example.backend.ghn.DTO;

import com.example.backend.util.Client;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

@Client
public class GHNWardDTO {
    @JsonAlias("WardCode") // Accepts "ProvinceID" when receiving data
    @JsonProperty("wardCode")
    private int wardCode;
    @JsonAlias("DistrictID") // Accepts "ProvinceID" when receiving data
    @JsonProperty("districtID")
    private int districtID;
    @JsonAlias("WardName") // Accepts "ProvinceID" when receiving data
    @JsonProperty("wardName")
    private String wardName;

    public int getWardCode() {
        return wardCode;
    }

    public void setWardCode(int wardCode) {
        this.wardCode = wardCode;
    }

    public int getDistrictID() {
        return districtID;
    }

    public void setDistrictID(int districtID) {
        this.districtID = districtID;
    }

    public String getWardName() {
        return wardName;
    }

    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
}
