package com.example.backend.thirtParty.ghn.DTO;

import java.util.List;

import com.example.backend.thirtParty.ghn.GHNConfig;
import com.example.backend.thirtParty.telosys.rest.dto.ShippingInfoDTO;
import com.example.backend.util.Client;

@Client
public class GHNFeeResquestDTO {
    private int from_district_id;
    private String from_ward_code;
    private String to_ward_code;
    private int to_district_id;
    private int service_id;
    private Integer service_type_id;
    private int height;
    private int length;
    private int weight;
    private int width;
    private int insurance_value;
    private int cod_failed_amount;
    private String coupon;
    private List<Item> items;

    // Getters and Setters

    public static class Item {
        private String name;
        private int quantity;
        private int height;
        private int weight;
        private int length;
        private int width;

        public Item(String name, int quantity) {
            this.name = name;
            this.quantity = quantity;
            this.height = 27;
            this.weight = 500;
            this.length = 21;
            this.width = 5;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        // Getters and Setters
    }

    public int getFrom_district_id() {
        return from_district_id;
    }

    public void setFrom_district_id(int from_district_id) {
        this.from_district_id = from_district_id;
    }

    public String getFrom_ward_code() {
        return from_ward_code;
    }

    public void setFrom_ward_code(String from_ward_code) {
        this.from_ward_code = from_ward_code;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public Integer getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(Integer service_type_id) {
        this.service_type_id = service_type_id;
    }

    public int getTo_district_id() {
        return to_district_id;
    }

    public void setTo_district_id(int to_district_id) {
        this.to_district_id = to_district_id;
    }

    public String getTo_ward_code() {
        return to_ward_code;
    }

    public void setTo_ward_code(String to_ward_code) {
        this.to_ward_code = to_ward_code;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getInsurance_value() {
        return insurance_value;
    }

    public void setInsurance_value(int insurance_value) {
        this.insurance_value = insurance_value;
    }

    public int getCod_failed_amount() {
        return cod_failed_amount;
    }

    public void setCod_failed_amount(int cod_failed_amount) {
        this.cod_failed_amount = cod_failed_amount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public void setConfigField(ShippingInfoDTO shippingInfoDTO, int totalQuantity, int service_id2, List<Item> gHNFeeResquestDTOItem) {
        setService_id(service_id2);
        setFrom_district_id(GHNConfig.shop_disctrict_id);
        setFrom_ward_code(GHNConfig.shop_ward_code + "");
        setTo_district_id(shippingInfoDTO.getDisctrictId());
        setTo_ward_code(shippingInfoDTO.getWardCode() + "");
        setHeight(27);
        setLength(21);
        setWeight(500 * totalQuantity);
        setWidth(5 * totalQuantity);
        setItems(gHNFeeResquestDTOItem);
    }
}