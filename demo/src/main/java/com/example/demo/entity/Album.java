package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangquanwei
 */
@Data
public class Album implements Serializable {
    private String name;
    private String tracks;
    private String musicians;
}
