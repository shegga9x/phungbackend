package com.example.backend.ghn;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.backend.ghn.DTO.GHNDistrictDTO;
import com.example.backend.ghn.DTO.GHNFeeResponseDTO;
import com.example.backend.ghn.DTO.GHNFeeResquestDTO;
import com.example.backend.ghn.DTO.GHNAvailableServicesDTO;
import com.example.backend.ghn.DTO.GHNProvinceDTO;
import com.example.backend.ghn.DTO.GHNWardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GHNService {
    private final GHNApiClient gHNApiClient;

    public GHNService(GHNApiClient gHNApiClient) {
        this.gHNApiClient = gHNApiClient;
    }

    public List<GHNProvinceDTO> getProvinces() {
        @SuppressWarnings("unchecked")
        List<GHNProvinceDTO> provinces = (List<GHNProvinceDTO>) gHNApiClient.getProvinces().getData();
        return provinces;
    }

    public List<GHNDistrictDTO> getDistricts(int province_id) {
        @SuppressWarnings("unchecked")
        List<GHNDistrictDTO> districts = (List<GHNDistrictDTO>) gHNApiClient.getDistricts(province_id).getData();
        return districts;
    }

    public List<GHNWardDTO> getWards(int district_id) {
        @SuppressWarnings("unchecked")
        List<GHNWardDTO> wards = (List<GHNWardDTO>) gHNApiClient.getWards(district_id).getData();
        return wards;
    }

    public List<GHNAvailableServicesDTO> getAvailableServices(int district_id,
            int from_district,
            int to_district) {
        @SuppressWarnings("unchecked")
        List<GHNAvailableServicesDTO> available_services = (List<GHNAvailableServicesDTO>) gHNApiClient
                .getAvailableServices(district_id, from_district, to_district).getData();
        return available_services;
    }

    public GHNFeeResponseDTO getFee(GHNFeeResquestDTO gHNFeeResquest) {
        ObjectMapper objectMapper = new ObjectMapper();
        GHNFeeResponseDTO feeResponse = objectMapper.convertValue(gHNApiClient
                .getFee(gHNFeeResquest).getData(), GHNFeeResponseDTO.class);
        return feeResponse;
    }

}