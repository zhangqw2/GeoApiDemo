package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhangquanwei
 */
@Data
public class Artist implements Serializable {
    private String name;
    private String members;
    private String origin;
    private Album album;
    private Track track;

    public boolean isFrom(String origin){
        return this.origin.equals(origin);
    }
}
