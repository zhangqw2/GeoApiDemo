package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangquanwei
 */
@Data
@ApiModel("经纬度坐标参数")
public class Distance {

    @ApiModelProperty(value = "维度1")
    private double lon1;
    @ApiModelProperty(value = "经度1")
    private double lat1;
    @ApiModelProperty(value = "维度2")
    private double lon2;
    @ApiModelProperty(value = "经度2")
    private double lat2;
}
