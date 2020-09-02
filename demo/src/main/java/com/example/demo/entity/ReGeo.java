package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangquanwei
 */
@Data
@ApiModel("逆地址编码")
public class ReGeo {
    /**
     * 高德key,必选
     */
    @ApiModelProperty(value = "高德key,必选",example = "7f4ffae4074e8b8e4d147190527a4b72")
    private String key;
    /**
     * 经纬度坐标,必选
     * 传入内容规则：经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不要超过 6 位。如果需要解析多个经纬度的话，请用"|"进行间隔，并且将 batch 参数设置为 true，最多支持传入 20 对坐标点。每对点坐标之间用"|"分割。
     */
    @ApiModelProperty(value = "经纬度坐标,必选",example = "116.481488,39.990464")
    private String location;
    /**
     * 返回附近POI类型,可选
     *以下内容需要 extensions 参数为 all 时才生效。
     *
     * 逆地理编码在进行坐标解析之后不仅可以返回地址描述，也可以返回经纬度附近符合限定要求的POI内容（在 extensions 字段值为 all 时才会返回POI内容）。设置 POI 类型参数相当于为上述操作限定要求。参数仅支持传入POI TYPECODE，可以传入多个POI TYPECODE，相互之间用“|”分隔。该参数在 batch 取值为 true 时不生效。
     */
    @ApiModelProperty(value = "返回附近POI类型,可选")
    private String poitype;
    /**
     * 搜索半径,可选
     * radius取值范围在0~3000，默认是1000。单位：米
     */
    @ApiModelProperty(value = "搜索半径,可选",example = "10")
    private String radius;
    /**
     * 返回结果控制,可选
     *
     *
     * extensions 参数默认取值是 base，也就是返回基本地址信息；
     *
     * extensions 参数取值为 all 时会返回基本地址信息、附近 POI 内容、道路信息以及道路交叉口信息。
     */
    @ApiModelProperty(value = "返回结果控制,可选",example = "all")
    private String extensions;
    /**
     * 批量查询控制,可选
     * batch 参数设置为 true 时进行批量查询操作，最多支持 20 个经纬度点进行批量地址查询操作。
     *
     * batch 参数设置为 false 时进行单点查询，此时即使传入多个经纬度也只返回第一个经纬度的地址解析查询结果。
     */
    @ApiModelProperty(value = "批量查询控制,可选")
    private boolean batch;
    /**
     * 道路等级 ,可选
     */
    @ApiModelProperty(value = "道路等级 ,可选")
    private String roadlevel;

    /**
     * 数字签名,可选
     */
    @ApiModelProperty(value = "数字签名,可选")
    private String sig;

    /**
     * 数据格式类型,可选
     *可选输入内容包括：JSON，XML。设置 JSON 返回结果数据将会以JSON结构构成；如果设置 XML 返回结果数据将以 XML 结构构成。
     */
    @ApiModelProperty(value = "数据格式类型,可选",example = "JSON")
    private String output;

    /**
     * callback 值是用户定义的函数名称，此参数只在 output 参数设置为 JSON 时有效。
     */
    @ApiModelProperty("回调函数")
    private String callback;

    /**
     * 是否优化POI返回顺序
     * 以下内容需要 extensions 参数为 all 时才生效。
     *
     * homeorcorp 参数的设置可以影响召回 POI 内容的排序策略，目前提供三个可选参数：
     *
     * 0：不对召回的排序策略进行干扰。
     *
     * 1：综合大数据分析将居家相关的 POI 内容优先返回，即优化返回结果中 pois 字段的poi顺序。
     *
     * 2：综合大数据分析将公司相关的 POI 内容优先返回，即优化返回结果中 pois 字段的poi顺序。
     */
    @ApiModelProperty("是否优化POI返回顺序")
    private String homeocorp;
}
