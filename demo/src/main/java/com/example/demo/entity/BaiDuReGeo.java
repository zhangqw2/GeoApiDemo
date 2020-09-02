package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangquanwei
 */
@Data
@ApiModel("百度逆地址编码")
public class BaiDuReGeo {
    /**
     * 百度key,必选
     */
    @ApiModelProperty(value = "百度key,必选",example = "G4GY2Rn77RH1w23jdFMoD5LMwvODdUW2")
    private String ak;
    /**
     * 经纬度坐标,必选
     * 传入内容规则：经度在前，纬度在后，经纬度间以“,”分割，经纬度小数点后不要超过 6 位。
     */
    @ApiModelProperty(value = "经纬度坐标,必选",example = "30.286307,120.022796")
    private String location;
    /**
     * 坐标的类型，目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度
     */
    @ApiModelProperty(value = "坐标的类型,可选",example = "bd09ll")
    private String coordtype;

    /**
     * 可选参数，添加后返回国测局经纬度坐标或百度米制坐标,可选
     */
    @ApiModelProperty(value = "坐标类型",example = "bd09ll")
    private String ret_coordtype ;

    /**
     * 搜索半径,可选
     * radius取值范围在0~1000，超过1000按1000。单位：米
     */
    @ApiModelProperty(value = "搜索半径,可选",example = "10")
    private int radius;

    @ApiModelProperty(value = "签名,可选")
    private String sn;

    /**
     * 数据格式类型,可选
     *可选输入内容包括：json，xml。设置 JSON 返回结果数据将会以JSON结构构成；如果设置 XML 返回结果数据将以 XML 结构构成。
     */
    @ApiModelProperty(value = "数据格式类型,可选",example = "json")
    private String output;

    /**
     * 将json格式的返回值通过callback函数返回以实现jsonp功能
     */
    @ApiModelProperty(value = "回调函数",example = "showLocation")
    private String callback;

    /**
     * extensions_poi=0，不召回pois数据。
     * extensions_poi=1，返回pois数据，默认显示周边1000米内的poi。
     */
    @ApiModelProperty(value = "扩展POI标识",example = "0")
    private String extensions_poi;
    /**
     * 当取值为true时，召回坐标周围最近的3条道路数据。
     */
    @ApiModelProperty(value = "道路标识",example = "false")
    private String extensions_road;
    /**
     * 当取值为true时，行政区划返回乡镇级数据（仅国内召回乡镇数据），默认不访问
     */
    @ApiModelProperty(value = "乡镇标识",example = "true")
    private String extensions_town;

    @ApiModelProperty(value = "语言",example = "zh-CN")
    private String language;
    @ApiModelProperty(value = "是否自动填充行政区划",example = "0")
    private int language_auto;
}
