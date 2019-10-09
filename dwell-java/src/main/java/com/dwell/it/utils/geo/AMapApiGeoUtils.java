package com.dwell.it.utils.geo;

import com.dwell.it.entities.House;
import com.dwell.it.model.geo.GeoInfoResponse;
import com.google.gson.Gson;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AMapApiGeoUtils {

    // 高德地图解析不到定位时的默认位置
    private final String defaultLocationGeo = "0,0";

    // 高德地图请求url前缀
    private final String amapRequestUrlPrefix = "https://restapi.amap.com/v3/geocode/geo";

    // 高德地图requestKey
    private final String amapRequestKey = "c9892e409b0997c95e2399acf0f3dcd4";

    // HttpRequestSuccessfulStatusCode
    private final String httpResponseType = "application/json";

    // Final API-Response-Key of Target Location
    private final String responsedLocationKey = "location";


    private final int HTTP_REQUEST_SUCCESSFULLY_CODE = 200;

    private static final Logger logger = LoggerFactory.getLogger(AMapApiGeoUtils.class);


    /**
     * @param qualifiedHouseList 未含有geo地理信息的数据源
     * @return 添加geo地理信息后的数据
     */
    public List<String> fetchQualifiedHouseListAfterRequestedAMapApi(List<House> qualifiedHouseList) {
        List<String> geoInfoList = new ArrayList<>(qualifiedHouseList.size());
        for (House item : qualifiedHouseList) {
            System.out.println(item.getCityZone().trim());
            String[] midAddress = item.getCityZone().trim().split("-");
            String address = midAddress[midAddress.length - 1];
            String location = fetchGeoInfo(address.trim());
            item.setGeoInfo(location);
            geoInfoList.add(location);
        }
        return geoInfoList;
    }


    /**
     * 获取 由文本地址在高德地图解析后的经纬度坐标信息
     *
     * @param address 文本地址信息
     * @return resultGeoInfo (地址坐标信息)
     */
    private String fetchGeoInfo(String address) {
        String geoInfo = "";
        String resultGeoInfo = invokeAmapGeoConvertApiForFinalGeoInfo(constructRequestURL(address));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            String runningMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            logger.error(runningMethodName + "" + e.getMessage());
        } finally {
            geoInfo = resultGeoInfo;
        }
        return geoInfo.length() > 0 ? geoInfo : defaultLocationGeo;
    }


    /**
     * 构造高德地图api的请求URL
     *
     * @param address 地址信息
     * @return url
     */
    private String constructRequestURL(String address) {
        String requestUrl = String.format("%s?address=%s&citycode=029&key=%s", amapRequestUrlPrefix, address, amapRequestKey);
        return requestUrl.length() > 0 ? requestUrl : "";
    }


    /**
     * 按顺序请求高德地图API，获取转换后的GEO数据
     *
     * @param url 请求url
     * @return 最终的geo数据
     */
    private String invokeAmapGeoConvertApiForFinalGeoInfo(String url) {
        CloseableHttpClient httpClient = HttpClientBuilder.create().disableCookieManagement().useSystemProperties().build();
        HttpUriRequest getRequest = new HttpGet(url);
        getRequest.addHeader(HttpHeaders.ACCEPT, httpResponseType);
        String result = defaultLocationGeo;

        try (CloseableHttpResponse httpResponse = httpClient.execute(getRequest)) {
            String content = EntityUtils.toString(httpResponse.getEntity());
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            if (statusCode == HTTP_REQUEST_SUCCESSFULLY_CODE) {
                Gson jsonHelper = new Gson();
                GeoInfoResponse geoModel = jsonHelper.fromJson(content, GeoInfoResponse.class);
                if (geoModel.getGeocodes() == null) {
                    return result;
                } else {
                    for (HashMap<String, Object> item : geoModel.getGeocodes()) {
                        String geo = item.get(responsedLocationKey).toString();
                        result = geo.length() > 0 ? geo : defaultLocationGeo;
                    }
                }
            }
        } catch (IOException ex) {
            String runningMethodName = Thread.currentThread().getStackTrace()[1].getMethodName();
            logger.error(runningMethodName + "" + ex.getMessage());
        }
        return result;
    }
}
