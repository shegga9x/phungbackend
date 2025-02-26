package com.example.backend.ghn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.ghn.DTO.GHNDistrictDTO;
import com.example.backend.ghn.DTO.GHNFeeResponseDTO;
import com.example.backend.ghn.DTO.GHNAvailableServicesDTO;
import com.example.backend.ghn.DTO.GHNProvinceDTO;
import com.example.backend.ghn.DTO.GHNWardDTO;
import com.example.backend.util.Client;

@RestController
@Client
@RequestMapping(value = "/api/v1/ghn")
public class GHNController {
    @Autowired
    private GHNService gHNService;

    @GetMapping("/provinces")
    public List<GHNProvinceDTO> getProvinces() {
        return gHNService.getProvinces();
    }

    @GetMapping("/districts")
    public List<GHNDistrictDTO> getDistricts(@RequestParam int province_id) {
        return gHNService.getDistricts(province_id);
    }

    @GetMapping("/wards")
    public List<GHNWardDTO> getWards(@RequestParam int district_id) {
        return gHNService.getWards(district_id);
    }

    @GetMapping("/available-services")
    public List<GHNAvailableServicesDTO> getAvailableServices(
            @RequestParam("user_id") Long user_id) {
        return gHNService.getAvailableServices(user_id);
    };

    @GetMapping("/fee")
    GHNFeeResponseDTO getFee(@RequestParam String bookId, @RequestParam int service_id, @RequestParam Long userId) {
        return gHNService.getFee(service_id, bookId, userId);
    }
}
