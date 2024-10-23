package com.zzyl.test;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @Descriptioin HttpUtilTest
 * @Author AvA
 * @Date 2024-10-20
 */
public class HttpUtilTest {
    @Test
    public void testGet() {
        // 最简单的HTTP请求，可以自动通过header等信息判断编码，不区分HTTP和HTTPS
        String result = HttpUtil.get("https://www.baidu.com");
        System.out.println(result);
    }

    @Test
    public void testGetByParam() {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", 10);
        String result = HttpUtil.get("http://localhost:9995/nursing_project", paramMap);
        System.out.println(result);
    }

    @Test
    public void testCreateRequest() {
        String url = "http://localhost:9995/nursing_project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageNum", 1);
        paramMap.put("pageSize", 10);
        HttpResponse response = HttpUtil.createRequest(Method.GET, url)
                .form(paramMap)
                .execute();
        if (response.getStatus() == 200) {
            System.out.println(response.body());
        }
    }

    @Test
    public void testPost(){
        String url = "http://localhost:9995/nursing_project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);
        String result = HttpUtil.post(url, JSONUtil.toJsonStr(paramMap));
        System.out.println(result);
    }

    @Test
    public void testPost2(){
        String url = "http://localhost:9995/nursing_project";
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "护理项目测试2");
        paramMap.put("orderNo", 1);
        paramMap.put("unit", "次");
        paramMap.put("price", 10.00);
        paramMap.put("image", "https://yjy-slwl-oss.oss-cn-hangzhou.aliyuncs.com/ae7cf766-fb7b-49ff-a73c-c86c25f280e1.png");
        paramMap.put("nursingRequirement", "无特殊要求");
        paramMap.put("status", 1);
        HttpResponse response = HttpUtil.createRequest(Method.POST, url)
                .body(JSONUtil.toJsonStr(paramMap))
                .execute();
        if(response.getStatus() == 200){
            System.out.println(response.body());
        }
    }

    @Test
    public void testWeater() {
        String url = "https://aliv18.data.moji.com/whapi/json/alicityweather/forecast24hours";
        String appcode = "f6f6168e5c1c4ae586ddab17909e9b75";//修改成自己的appcode
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("cityId", "1945");

        HttpResponse response = HttpUtil.createRequest(Method.POST, url)
                .header("Authorization", "APPCODE " + appcode)
                .form(param)
                .execute();
        if (response.getStatus() == 200) {
            System.out.println(response.body());
        }
    }

    @Test
    public void testLogisticsInformation(){
        String host = "https://lhkdcx.market.alicloudapi.com";
        String path = "/express/query";
        String url = host+path;
        String appcode = "3327ec8399984ebb891fc8bcbd603d4b";
        HashMap<String, Object> bodys = new HashMap<String, Object>();
        bodys.put("expressNo", "expressNo");
        bodys.put("waybillNo", "JT5309914590202");
        bodys.put("mobile", "18835600884");

        HttpResponse response = HttpUtil.createRequest(Method.POST,url)
                .header("Authorization","APPCODE " + appcode)
                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .form(bodys)
                .execute();

        if(response.getStatus() == 200){
            System.out.println(response.body());
        }
    }

    @Test
    public void test(){
        String resourceNo = "100001006000000";
        int step = resourceNo.length()/3;
        int supplement = 0;
        for (int i = 0; i < step; i++){
            String targetString = resourceNo.substring(resourceNo.length()-3,resourceNo.length());
            if("000".equals(targetString)){
                resourceNo = resourceNo.substring(0,resourceNo.length()-3);
                supplement++;
            } else {
                break;
            }
        }
    }
}
