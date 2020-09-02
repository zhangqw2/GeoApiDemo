package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangquanwei
 */
@Data
@ApiModel("百度地理编码参数")
public class BaiDuGeo {

    /**
     * 百度key,必选
     */
    @ApiModelProperty(value = "百度key,必选",example = "G4GY2Rn77RH1w23jdFMoD5LMwvODdUW2")
    private String ak;
    /**
     * 待解析的地址。最多支持84个字节。
     * 可以输入两种样式的值，分别是：
     * 1、标准的结构化地址信息，如北京市海淀区上地十街十号 【推荐，地址结构越完整，解析精度越高】
     * 2、支持“*路与*路交叉口”描述方式，如北一环路和阜阳路的交叉路口
     * 第二种方式并不总是有返回结果，只有当地址库中存在该地址描述时才有返回。
     *
     */
    @ApiModelProperty(value = "结构化地址信息,必选",example = "北京市朝阳区阜通东大街6号")
    private String address;
    /**
     * 指定查询城市,可选
     *
     */
    @ApiModelProperty(value = "城市",example = "杭州")
    private String city;
    /**
     * 可选参数，添加后返回国测局经纬度坐标或百度米制坐标,可选
     */
    @ApiModelProperty(value = "坐标类型",example = "false")
    private String ret_coordtype ;
    /**
     * 数字签名,可选
     */
    @ApiModelProperty(value = "数字签名")
    private String sn;
    /**
     * 返回数据格式类型,可选
     * 可选输入内容包括：json，xml。设置 JSON 返回结果数据将会以JSON结构构成；如果设置 XML 返回结果数据将以 XML 结构构成。
     */
    @ApiModelProperty(value = "数据格式类型",example = "json")
    private String output;
    /**
     * 将json格式的返回值通过callback函数返回以实现jsonp功能
     */
    @ApiModelProperty(value = "回调函数",example = "showLocation")
    private String callback;

    @ApiModelProperty(value = "ip")
    private String ip;
}
