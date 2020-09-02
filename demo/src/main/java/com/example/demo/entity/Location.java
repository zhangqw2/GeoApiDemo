package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangquanwei
 */
@Data
public class Location implements Serializable {
    @JSONField(name = "lat")
    private String lat;
    @JSONField(name = "lng")
    private String lng;
}
