package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangquanwei
 */
@Data
@ApiModel("百度地点检索服务参数")
public class BaiduPlaceSearch {


    /**
     * poi的uid
     */
    private String uid;

    /**
     * 请求返回格式
     */
    @ApiModelProperty(value = "请求返回格式" , example = "json")
    private String output;
    /**
     * 检索结果详细程度。取值为1 或空，则返回基本信息；取值为2，返回检索POI详细信息
     */
    @ApiModelProperty(value = "检索结果详细程度。取值为1 或空，则返回基本信息；取值为2，返回检索POI详细信息",example = "2")
    private String scope;
    /**
     * 百度key,必选
     */
    @ApiModelProperty(value = "百度key,必选",example = "G4GY2Rn77RH1w23jdFMoD5LMwvODdUW2")
    private String ak;

}
