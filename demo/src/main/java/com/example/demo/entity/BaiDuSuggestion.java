package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhangquanwei
 */
@Data
public class BaiDuSuggestion implements Serializable {
    @JSONField(name = "status")
    private Integer status;
    @JSONField(name = "message")
    private String message;

    @JSONField(name = "result")
    private List<Map<String,Object>> result;
}
