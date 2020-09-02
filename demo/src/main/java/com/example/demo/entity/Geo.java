package com.example.demo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * @author zhangquanwei
 */
@Data
public class Geo {

    /**
     * 高德key,必选
     */
    @ApiModelProperty(value = "高德key,必选",example = "7f4ffae4074e8b8e4d147190527a4b72")
    private String key;
    /**
     * 结构化地址信息,必选
     * 规则遵循：国家、省份、城市、区县、城镇、乡村、街道、门牌号码、屋邨、大厦，如：北京市朝阳区阜通东大街6号。如果需要解析多个地址的话，请用"|"进行间隔，并且将 batch 参数设置为 true，最多支持 10 个地址进进行"|"分割形式的请求。
     */
    @ApiModelProperty(value = "结构化地址信息,必选",example = "北京市朝阳区阜通东大街6号")
    private String address;
    /**
     * 指定查询城市,可选
     * 可选输入内容包括：指定城市的中文（如北京）、指定城市的中文全拼（beijing）、citycode（010）、adcode（110000），不支持县级市。当指定城市查询内容为空时，会进行全国范围内的地址转换检索。
     */
    @ApiModelProperty(value = "城市",example = "杭州")
    private String city;
    /**
     * 批量查询控制,最多支持10个,可选
     */
    @ApiModelProperty(value = "批量查询",example = "false")
    private boolean batch ;
    /**
     * 数字签名,可选
     */
    @ApiModelProperty(value = "数字签名")
    private String sig;
    /**
     * 返回数据格式类型,可选
     * 可选输入内容包括：JSON，XML。设置 JSON 返回结果数据将会以JSON结构构成；如果设置 XML 返回结果数据将以 XML 结构构成。
     */
    @ApiModelProperty(value = "数据格式类型",example = "JSON")
    private String output;
    /**
     * 回调函数
     * callback 值是用户定义的函数名称，此参数只在 output 参数设置为 JSON 时有效。
     */
    @ApiModelProperty(value = "回调函数")
    private String callback;

    @ApiModelProperty(value = "ip")
    private String ip;

}
