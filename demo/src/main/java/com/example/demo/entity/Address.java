package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangquanwei
 */
@Data
@ApiModel("地址信息")
public class Address {
    @ApiModelProperty(value = "国家")
    @JSONField(name = "cn_country")
    private String country;
    @ApiModelProperty(value = "省份")
    @JSONField(name = "cn_province")
    private String province;
    @ApiModelProperty(value = "城市")
    @JSONField(name = "cn_city")
    private String city;
}
