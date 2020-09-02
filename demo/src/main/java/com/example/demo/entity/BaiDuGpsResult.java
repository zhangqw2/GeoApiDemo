package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangquanwei
 */
@Data
public class BaiDuGpsResult implements Serializable {
    @JSONField(name = "status")
    private Integer status;
    @JSONField(name = "result")
    private Result result;


    @Data
    public static class Result implements Serializable{
        @JSONField(name = "formatted_address")
        private String formattedAddress;

        @JSONField(name = "business")
        private String business;

        @JSONField(name = "cityCode")
        private String cityCode;
        @JSONField(name = "location")
        private Location location;
        @JSONField(name = "addressComponent")
        private AddressComponent addressComponent;

    }

    @Data
    public static class AddressComponent implements Serializable{
        @JSONField(name = "city_level")
        private Integer cityLevel;
        @JSONField(name = "country")
        private String country;
        @JSONField(name = "province")
        private String province;
        @JSONField(name = "city")
        private String city;
        @JSONField(name = "town")
        private String town;
        @JSONField(name = "street")
        private String street;
        @JSONField(name = "district")
        private String district;
        @JSONField(name = "town_code")
        private String townCode;
    }



}
