package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.MD5;
import io.swagger.annotations.Api;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author zhangquanwei
 */
@Api("测试接口")
@Controller("ok")
@RequestMapping("/ok")
public class OkController {
    public static String reportId = "test";

    @RequestMapping(value = "/test",method = RequestMethod.GET,produces = "application/json;utf-8")
    @ResponseBody
    public String test(String abc) {
        return reportId;
    }

    @RequestMapping(value = "/map{map}",method = RequestMethod.GET,produces = "application/json;utf-8")
    @ResponseBody
    public Map<String,Object> getMap(@PathVariable  Map<String,Object> map) {
        System.out.println(map);
        return JSONObject.parseObject(map.get("map").toString());
    }
    @RequestMapping(value = "/md5",method = RequestMethod.GET,produces = "application/json;utf-8")
    @ResponseBody
    public String MD5(@RequestParam  String name, @RequestParam String id){
        String md5 = DigestUtils.md5Hex(name+id);
        return md5;
    }

    @RequestMapping("/index")
    public String index(ModelMap map){
        //单个数据
        map.put("username", "加密");
        MD5 md5 = new MD5();
        md5.setId("zqw");
        md5.setName("测试");
        map.put("userInfo", md5);
        return "/index";
    }


    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String add(@ModelAttribute MD5 md5,ModelMap map){
        String name = md5.getName();
        String id = md5.getId();
        map.put("username", "加密结果");
        map.put("id",id);
        map.put("name",name);
        map.put("MD5",DigestUtils.md5Hex(md5.getId()+md5.getName()));
        return "result";
    }
}
