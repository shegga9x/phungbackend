package com.example.backend.thirtParty.ghn;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.backend.thirtParty.ghn.DTO.GHNAvailableServicesDTO;
import com.example.backend.thirtParty.ghn.DTO.GHNDistrictDTO;
import com.example.backend.thirtParty.ghn.DTO.GHNFeeResponseDTO;
import com.example.backend.thirtParty.ghn.DTO.GHNFeeResquestDTO;
import com.example.backend.thirtParty.ghn.DTO.GHNProvinceDTO;
import com.example.backend.thirtParty.ghn.DTO.GHNWardDTO;
import com.example.backend.thirtParty.ghn.DTO.GHNFeeResquestDTO.Item;
import com.example.backend.thirtParty.telosys.rest.dto.BooksDTO;
import com.example.backend.thirtParty.telosys.rest.dto.CartItemDTO;
import com.example.backend.thirtParty.telosys.rest.dto.CartResponse;
import com.example.backend.thirtParty.telosys.rest.dto.ShippingInfoDTO;
import com.example.backend.thirtParty.telosys.rest.services.BooksService;
import com.example.backend.thirtParty.telosys.rest.services.CartItemService;
import com.example.backend.thirtParty.telosys.rest.services.ShippingInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class GHNService {
    private final GHNApiClient gHNApiClient;
    private ShippingInfoService shippingInfoService;
    private CartItemService cartItemService;
    private BooksService booksService;

    public GHNService(GHNApiClient gHNApiClient, ShippingInfoService shippingInfoService,
            CartItemService cartItemService, BooksService booksService) {
        this.gHNApiClient = gHNApiClient;
        this.shippingInfoService = shippingInfoService;
        this.cartItemService = cartItemService;
        this.booksService = booksService;
    }

    public List<GHNProvinceDTO> getProvinces() {
        List<GHNProvinceDTO> provinces = gHNApiClient.getProvinces().getData();
        return provinces;
    }

    public List<GHNDistrictDTO> getDistricts(int province_id) {
        List<GHNDistrictDTO> districts = gHNApiClient.getDistricts(province_id).getData();
        return districts;
    }

    public List<GHNWardDTO> getWards(int district_id) {
        List<GHNWardDTO> wards = (List<GHNWardDTO>) gHNApiClient.getWards(district_id).getData();
        return wards;
    }

    public List<GHNAvailableServicesDTO> getAvailableServices(Long user_id) {
        ShippingInfoDTO shippingInfoDTO = shippingInfoService.findByUserId(user_id).get(0);

        List<GHNAvailableServicesDTO> available_services = (List<GHNAvailableServicesDTO>) gHNApiClient
                .getAvailableServices(GHNConfig.shop_id,
                        GHNConfig.shop_disctrict_id, shippingInfoDTO.getDisctrictId())
                .getData();
        return available_services;
    }

    public GHNFeeResponseDTO getFee(int service_id, String bookId, Long userId) {
        int totalQuantity = 0;
        List<Item> gHNFeeResquestDTOItem = new ArrayList<>();
        if (bookId.equals("null")) {
            List<CartResponse> cartItems = cartItemService.findByUserId(userId);
            totalQuantity = cartItems.stream()
                    .mapToInt(item -> item.getQuantity())
                    .sum();
            for (CartResponse cartResponse : cartItems) {
                totalQuantity += cartResponse.getQuantity();
                gHNFeeResquestDTOItem.add(new Item(cartResponse.getTitle(), cartResponse.getQuantity()));
            }
        } else {
            Long bookIdNew = Long.parseLong(bookId);
            CartItemDTO cartItemDTO = cartItemService.findById(bookIdNew, userId);
            BooksDTO booksDTO = booksService.findById(bookIdNew);
            totalQuantity = cartItemDTO.getQuantity();
            gHNFeeResquestDTOItem.add(new Item(booksDTO.getTitle(), cartItemDTO.getQuantity()));
        }
        ShippingInfoDTO shippingInfoDTO = shippingInfoService.findByUserId(userId).get(0);
        GHNFeeResquestDTO gHNFeeResquestDTO = new GHNFeeResquestDTO();
        gHNFeeResquestDTO.setConfigField(shippingInfoDTO, totalQuantity, service_id, gHNFeeResquestDTOItem);
        ObjectMapper objectMapper = new ObjectMapper();
        GHNFeeResponseDTO feeResponse = objectMapper.convertValue(gHNApiClient
                .getFee(gHNFeeResquestDTO).getData(), GHNFeeResponseDTO.class);
        return feeResponse;
    }

    public BigDecimal getShippingFeeAndTotalPrice(int service_id, String bookId, Long userId) {
        int totalQuantity = 0;
        List<Item> gHNFeeResquestDTOItem = new ArrayList<>();
        BigDecimal totalBooksPrice = new BigDecimal(0);
        if (bookId.equals("null")) {
            List<CartResponse> cartItems = cartItemService.findByUserId(userId);
            for (CartResponse cartResponse : cartItems) {
                totalQuantity += cartResponse.getQuantity();
                gHNFeeResquestDTOItem.add(new Item(cartResponse.getTitle(), cartResponse.getQuantity()));
                totalBooksPrice = totalBooksPrice
                        .add(cartResponse.getPrice().multiply(new BigDecimal(cartResponse.getQuantity())));
            }
        } else {
            Long bookIdNew = Long.parseLong(bookId);
            CartItemDTO cartItemDTO = cartItemService.findById(bookIdNew, userId);
            BooksDTO booksDTO = booksService.findById(bookIdNew);
            totalQuantity = cartItemDTO.getQuantity();
            gHNFeeResquestDTOItem.add(new Item(booksDTO.getTitle(), cartItemDTO.getQuantity()));
            totalBooksPrice = totalBooksPrice
                    .add(booksDTO.getPrice().multiply(new BigDecimal(cartItemDTO.getQuantity())));
        }
        ShippingInfoDTO shippingInfoDTO = shippingInfoService.findByUserId(userId).get(0);
        GHNFeeResquestDTO gHNFeeResquestDTO = new GHNFeeResquestDTO();
        gHNFeeResquestDTO.setConfigField(shippingInfoDTO, totalQuantity, service_id, gHNFeeResquestDTOItem);
        ObjectMapper objectMapper = new ObjectMapper();
        GHNFeeResponseDTO feeResponse = objectMapper.convertValue(gHNApiClient
                .getFee(gHNFeeResquestDTO).getData(), GHNFeeResponseDTO.class);
        totalBooksPrice = totalBooksPrice
                .multiply(new BigDecimal(25000*100));
        totalBooksPrice = totalBooksPrice
                .add(new BigDecimal(feeResponse.getTotal()));
        return totalBooksPrice;
    }


}