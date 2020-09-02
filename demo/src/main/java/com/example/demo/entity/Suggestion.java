package com.example.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhangquanwei
 */
@Data
@ApiModel("百度地点输入提示接口参数")
public class Suggestion {
    @ApiModelProperty(value = "查询公司名字",example = "同盾科技有限公司")
    private String query;
    @ApiModelProperty(value = "指定的区域的返回结果加权",example = "杭州")
    private String region;
    @ApiModelProperty(value = "取值为\"true\"，仅返回region中指定城市检索结果",example = "true")
    private String cityLimit;

    @ApiModelProperty(value = "传入location参数后，返回结果将以距离进行排序",example = "40.047857537164,116.31353434477")
    private String location;

    @ApiModelProperty(value = "数据格式类型",example = "json")
    private String output;

    @ApiModelProperty(value = "百度key,必选",example = "G4GY2Rn77RH1w23jdFMoD5LMwvODdUW2")
    private String ak;
}
