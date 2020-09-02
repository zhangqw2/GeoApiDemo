package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.http.Consts;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.Configurable;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangquanwei
 */
@Api("百度GPS接口文档")
@Controller("baiDu")
@RequestMapping("/baiDu")
public class BaiDuGpsController {

    private static Logger logger = LoggerFactory.getLogger(BaiDuGpsController.class);

    @Value("${baidu.geocode.geo.url}")
    private String geoUrl ;

    @Value("${baidu.geocode.reGeo.url}")
    private String reGeoUrl;

    @Value("${baidu.geo.http.ip.url}")
    private String geoIpUrl;

    @Value("${baidu.geo.https.ip.url}")
    private String geoIpUrls;

    @Value("http://api.map.baidu.com/place/v2/detail")
    private String placeUrl;

    @Value("http://api.map.baidu.com/place/v2/suggestion")
    private String suggestionUrl;

    @Value("http://api.map.baidu.com/place/v2/search")
    private String placeV2Url;
    @Autowired
    private  CloseableHttpClient httpClient;

    @ApiOperation(value = "获取经纬度",notes = "根据地址解析坐标")
    @RequestMapping(value = "coordinates",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public String getCoordinatesByAddress(@RequestBody BaiDuGeo baiDuGeo){
        if(baiDuGeo == null){
            return "address参数为空";
        }
        logger.info("address: {}",baiDuGeo.getAddress());
        Map<String, String> params = new HashMap<>();
        params.put("address",baiDuGeo.getAddress());
        params.put("output",baiDuGeo.getOutput());
        params.put("ak",baiDuGeo.getAk());
        try{
            String response = doGet(geoUrl,params,10000);
            logger.info("地址编码: {}",response);
            return response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    @ApiOperation(value = "获取详细地址",notes = "根据经纬度获取地址")
    @RequestMapping(value = "location",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public JSONObject getAddressByCoordinates(@RequestBody BaiDuReGeo baiDuReGeo){
        if(baiDuReGeo == null ){
            return null;
        }
        Map<String,String> params = new HashMap<>();
        params.put("ak",baiDuReGeo.getAk());
        params.put("output",baiDuReGeo.getOutput());
        params.put("location",baiDuReGeo.getLocation());
        try{
            String response = doGet(reGeoUrl,params,10000);
            logger.info("逆地理编码: [{}] -> \n {}",baiDuReGeo.getLocation(),response);
            return JSONObject.parseObject(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "获取地址",notes = "根据经纬度获取国家省市")
    @RequestMapping(value = "address",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public JSONObject getLocationByCoordinates(@RequestBody BaiDuReGeo baiDuReGeo){
        if(baiDuReGeo == null ){
            return new JSONObject();
        }
        Map<String,String> params = new HashMap<>();
        params.put("ak",baiDuReGeo.getAk());
        params.put("output",baiDuReGeo.getOutput());
        params.put("location",baiDuReGeo.getLocation());
        params.put("extensions_town","true");
        //params.put("extensions_poi","1");
        try{
            String response = doGet(reGeoUrl,params,10000);
            logger.info("逆地理编码: [{}] -> \n {}",baiDuReGeo.getLocation(),response);
            BaiDuGpsResult baiDuGpsResult = JSON.parseObject(response,BaiDuGpsResult.class);
            logger.info("BaiDuGpsResult {}",JSONObject.toJSONString(baiDuGpsResult));
            JSONObject jsonObject = JSONObject.parseObject(response);

            return jsonObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "通过HTTP获取IP地址归属地",notes = "通过HTTP根据IP获取国家省市")
    @RequestMapping(value = "ipLocationByHttp",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public JSONObject getLocationByHttpIp(@RequestBody BaiDuGeo geo){
        if(geo == null || geo.getIp() == null || "".equals(geo.getIp())){
            return new JSONObject();
        }
        Map<String, String> params = new HashMap<>();
        params.put("ip",geo.getIp());
        params.put("ak",geo.getAk());

        try{
            String response = doGet(geoIpUrl,params,10000);
            logger.info("IP地址归属地: [{}] -> \n {}",geo.getIp(),response);
            JSONObject ipLocationResponse = JSONObject.parseObject(response);
            return ipLocationResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "通过HTTPS获取IP地址归属地",notes = "通过HTTPS根据IP获取国家省市")
    @RequestMapping(value = "ipLocationByHttps",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public JSONObject getLocationByHttpsIp(@RequestBody BaiDuGeo geo){
        if(geo == null || geo.getIp() == null || "".equals(geo.getIp())){
            return new JSONObject();
        }
        Map<String, String> params = new HashMap<>();
        params.put("ip",geo.getIp());
        params.put("ak",geo.getAk());
        try{
            String response = doGet(geoIpUrls,params,10000);
            logger.info("IP地址归属地: [{}] -> \n {}",geo.getIp(),response);
            JSONObject ipLocationResponse = JSONObject.parseObject(response);
            return ipLocationResponse;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @ApiOperation(value= "通过关键字检索地址详情",notes = "通过关键字检索地址详情")
    @RequestMapping(value = "keyword",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public JSONObject getAddressInfoByKeyword(@RequestBody BaiduPlaceSearch placeSearch){
        if(placeSearch == null){
            return new JSONObject();
        }
        Map<String, String> params = new HashMap<>();
        params.put("uid",placeSearch.getUid());
        params.put("output",placeSearch.getOutput());
        params.put("scope",placeSearch.getScope());
        params.put("ak",placeSearch.getAk());
        try{
            String response = doGet(placeUrl,params,10000);
            logger.info("关键字检索地址详情 : [{}] -> \n {}",placeSearch,response);
            JSONObject Response = JSONObject.parseObject(response);
            return Response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    @ApiOperation(value= "通过关键字地点输入提示",notes = "通过关键字地点输入提示")
    @RequestMapping(value = "suggestion",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public JSONObject getSuggestionByKeyword(@RequestBody Suggestion suggestion){
        if(suggestion == null){
            return new JSONObject();
        }
        Map<String, String> params = new HashMap<>();
        params.put("query",suggestion.getQuery());
        params.put("region",suggestion.getRegion());
        params.put("city_limit",suggestion.getCityLimit());
        params.put("output",suggestion.getOutput());
        params.put("ak",suggestion.getAk());
        try{
            String response = doGet(suggestionUrl,params,10000);
            logger.info("通过关键字地点输入提示 : [{}] -> \n {}",suggestion,response);
            JSONObject Response = JSONObject.parseObject(response);
            BaiDuSuggestion baiDuSuggestion = JSON.parseObject(response,BaiDuSuggestion.class);
            logger.info(JSONObject.toJSONString(baiDuSuggestion));
            return Response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value= "行政区划区域检索",notes = "行政区划区域检索")
    @RequestMapping(value = "place",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public JSONObject getPlaceByKeyword(@RequestBody Suggestion suggestion){
        if(suggestion == null){
            return new JSONObject();
        }
        Map<String, String> params = new HashMap<>();
        params.put("query",suggestion.getQuery());
        params.put("region",suggestion.getRegion());
        params.put("city_limit",suggestion.getCityLimit());
        params.put("output",suggestion.getOutput());
        params.put("ak",suggestion.getAk());
        try{
            String response = doGet(placeV2Url,params,10000);
            logger.info("行政区划区域检索 : [{}] -> \n {}",suggestion,response);
            JSONObject Response = JSONObject.parseObject(response);
            return Response;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param distance
     * @return
     */
    @ApiOperation(value= "计算两地距离",notes = "计算两地距离")
    @RequestMapping(value = "distance",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public  double getDistance(@RequestBody Distance distance) {
        double long1 = distance.getLon1();
        double lat1 = distance.getLat1();
        double long2 = distance.getLon2();
        double lat2 = distance.getLat2();

        double a, b, R;
        // 地球半径
        R = 6378137;
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }
    /**
     * HTTP get请求
     * @param url
     * @param params
     * @param socketTimeout
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public   String doGet(String url, Map<String, String> params, int socketTimeout) throws IOException, URISyntaxException {
        URIBuilder uriBuilder = new URIBuilder(url);
        uriBuilder.setCharset(Consts.UTF_8).build();
        if (params != null) {
            params.forEach(uriBuilder::addParameter);
        }
        HttpGet httpGet = new HttpGet(uriBuilder.build());
        logger.info("请求地址: {}",httpGet.getURI().getPath());
        //设置请求头
        httpGet.addHeader(HttpHeaders.CONTENT_TYPE, "application/json;charset=UTF-8");

        return doHttp(httpGet, socketTimeout);
    }

    /**
     * HTTP请求公共方法
     * @param request
     * @param socketTimeout
     * @return
     * @throws IOException
     */
    private   String doHttp(HttpRequestBase request, int socketTimeout) throws IOException {

        //设置超时时间
        if (socketTimeout > 0) {
            //获取原有配置
            //实际注入类型org.apache.http.impl.client.InternalHttpClient
            Configurable configClient = (Configurable) httpClient;
            RequestConfig.Builder custom = RequestConfig.copy(configClient.getConfig());
            //设置个性化配置
            RequestConfig config = custom.setSocketTimeout(socketTimeout).build();
            request.setConfig(config);
        }
        ResponseHandler<String> handler = new BasicResponseHandler();
        long startPoint = System.currentTimeMillis();
        String response = httpClient.execute(request, handler);
        logger.info("请求耗时[{}], 接口返回信息[{}]", System.currentTimeMillis() - startPoint, response);
        return response;
    }
}
