package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Address;
import com.example.demo.entity.Geo;
import com.example.demo.entity.ReGeo;
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
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangquanwei
 */
@Api("高德GPS接口文档")
@Controller("gps")
@RequestMapping("/gps")
public class GpsController {

    private static Logger logger = LoggerFactory.getLogger(GpsController.class);

    @Value("${geocode.geo.url}")
    private String geoUrl ;

    @Value("${geocode.reGeo.url}")
    private String reGeoUrl;

    @Value("${geo.ip.url}")
    private String geoIpUrl;


    @Autowired
    private  CloseableHttpClient httpClient;

    @ApiOperation(value = "获取经纬度",notes = "根据地址解析坐标")
    @RequestMapping(value = "coordinates",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public String getCoordinatesByAddress(@RequestBody Geo geo){
        if(geo == null){
            return "address参数为空";
        }
        logger.info("address: {}",geo.getAddress());
        Map<String, String> params = new HashMap<>();
        params.put("address",geo.getAddress());
        params.put("output",geo.getOutput());
        params.put("key",geo.getKey());
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
    public JSONObject getAddressByCoordinates(@RequestBody ReGeo reGeo){
        if(reGeo == null ){
            return null;
        }
        Map<String, String> params = getParamsReGeo(reGeo);
        try{
            String response = doGet(reGeoUrl,params,10000);
            logger.info("逆地理编码: [{}] -> \n {}",reGeo.getLocation(),response);
            return JSONObject.parseObject(response);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "获取地址",notes = "根据经纬度获取国家省市")
    @RequestMapping(value = "address",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public Address getLocationByCoordinates(@RequestBody ReGeo reGeo){
        Address address = new Address();
        if(reGeo == null ){
            return address;
        }
        Map<String, String> params = getParamsReGeo(reGeo);
        try{
            String response = doGet(reGeoUrl,params,10000);
            logger.info("逆地理编码: [{}] -> \n {}",reGeo.getLocation(),response);
            JSONObject jsonObject = JSONObject.parseObject(response);
            JSONObject regeoObject = jsonObject.getJSONObject("regeocode");
            if(regeoObject.getString("country")!= null){
                String country = regeoObject.getString("country");
                address.setCountry(country);
            }
            JSONObject   addressComponent = regeoObject.getJSONObject("addressComponent");
            if(addressComponent.getString("city")!= null){
                String city = addressComponent.getString("city");
                address.setCity(city);
            }
            if(addressComponent.getString("province")!= null){
                String province = addressComponent.getString("province");
                address.setProvince(province);
            }
            return address;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @ApiOperation(value = "获取IP地址归属地",notes = "根据IP获取国家省市")
    @RequestMapping(value = "ipLocation",method = RequestMethod.POST,produces = "application/json;utf-8")
    @ResponseBody
    public Address getLocationByIp(@RequestBody Geo geo){
        Address address = new Address();
        if(geo == null || geo.getIp() == null || "".equals(geo.getIp())){
            return address;
        }
        Map<String, String> params = getParamsGeo(geo);
        try{
            String response = doGet(geoIpUrl,params,10000);
            logger.info("IP地址归属地: [{}] -> \n {}",geo.getIp(),response);
            JSONObject ipLocationResponse = JSONObject.parseObject(response);
            String city = ipLocationResponse.getString("city");
            address.setCity(city);
            String province = ipLocationResponse.getString("province");
            address.setProvince(province);
            return address;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 封装GEO
     * @param geo
     * @return
     */
    private Map<String,String> getParamsGeo(Geo geo){

        Map<String, String> params = new HashMap<>();
        params.put("ip",geo.getIp());
        params.put("output",geo.getOutput());
        params.put("key",geo.getKey());
        return params;
    }

    /**
     * 封装ReGeo
     *
     * @param reGeo
     * @return
     */
    private Map<String,String> getParamsReGeo(ReGeo reGeo){

        Map<String, String> params = new HashMap<>();
        params.put("location",reGeo.getLocation());
        params.put("output",reGeo.getOutput());
        params.put("key",reGeo.getKey());
        params.put("extensions",reGeo.getExtensions());
        params.put("radius",reGeo.getRadius());
        return params;
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
        //设置请求头
        httpGet.addHeader(HttpHeaders.CONTENT_TYPE, "text/html;charset=UTF-8");

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
