package com.example.backend.ghn;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.backend.ghn.DTO.GHNFeeResquestDTO;
import com.example.backend.ghn.DTO.GHNResponseDTO;

import feign.Headers;

@FeignClient(name = "externalApi", url = "https://dev-online-gateway.ghn.vn/shiip/public-api/", configuration = FeignClientConfig.class)
public interface GHNApiClient {

        @GetMapping("master-data/province")
        GHNResponseDTO getProvinces();

        @GetMapping("master-data/district")
        GHNResponseDTO getDistricts(@RequestParam("province_id") int province_id);

        @GetMapping("master-data/ward")
        GHNResponseDTO getWards(@RequestParam("district_id") int district_id);

        @GetMapping("v2/shipping-order/available-services")
        GHNResponseDTO getAvailableServices(
                        @RequestParam("shop_id") int shop_id,
                        @RequestParam("from_district") int from_district,
                        @RequestParam("to_district") int to_district);

        @PostMapping("v2/shipping-order/fee")
        GHNResponseDTO getFee(@RequestBody GHNFeeResquestDTO requestDTO);
}
