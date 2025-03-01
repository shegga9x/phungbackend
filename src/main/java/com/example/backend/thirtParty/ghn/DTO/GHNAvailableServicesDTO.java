package com.example.backend.thirtParty.ghn.DTO;

import com.example.backend.util.Client;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

@Client
public class GHNAvailableServicesDTO {
    @JsonAlias("service_id")
    @JsonProperty("serviceId")
    private int serviceId;
    @JsonAlias("short_name")
    @JsonProperty("shortName")
    private String shortName;
    @JsonAlias("service_type_id")
    @JsonProperty("serviceTypeId")
    private int serviceTypeId;

    // Getters and Setters
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(int serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }
}
