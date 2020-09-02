package com.example.demo.config;

import com.example.demo.entity.Address;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//@ControllerAdvice
public class ModelAttributeConfig {

//    @ModelAttribute("address")
//    public String getAddress(){
//        System.out.println("系统启动中。。。加载codeMap");
//        return "长城大厦";
//    }
    @PreDestroy
    public void destroy(){
        System.out.println("系统运行结束");
    }
}
