package com.example.backend.ghn;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.backend.ghn.DTO.GHNAvailableServicesDTO;
import com.example.backend.ghn.DTO.GHNDistrictDTO;
import com.example.backend.ghn.DTO.GHNFeeResponseDTO;
import com.example.backend.ghn.DTO.GHNFeeResquestDTO;
import com.example.backend.ghn.DTO.GHNProvinceDTO;
import com.example.backend.ghn.DTO.GHNResponseDTO;
import com.example.backend.ghn.DTO.GHNWardDTO;

@FeignClient(name = "gHNApi", url = "https://dev-online-gateway.ghn.vn/shiip/public-api/", configuration = FeignClientConfig.class)
public interface GHNApiClient {

        @GetMapping("master-data/province")
        GHNResponseDTO<List<GHNProvinceDTO>> getProvinces();

        @GetMapping("master-data/district")
        GHNResponseDTO<List<GHNDistrictDTO>> getDistricts(@RequestParam("province_id") int province_id);

        @GetMapping("master-data/ward")
        GHNResponseDTO<List<GHNWardDTO>> getWards(@RequestParam("district_id") int district_id);

        @GetMapping("v2/shipping-order/available-services")
        GHNResponseDTO<List<GHNAvailableServicesDTO>> getAvailableServices(
                        @RequestParam("shop_id") int shop_id,
                        @RequestParam("from_district") int from_district,
                        @RequestParam("to_district") int to_district);

        @PostMapping("v2/shipping-order/fee")
        GHNResponseDTO<GHNFeeResponseDTO> getFee(@RequestBody GHNFeeResquestDTO requestDTO);
}
